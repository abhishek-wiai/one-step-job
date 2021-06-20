package org.om.onestepjob.ui.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.om.onestepjob.OneStepJobApplication
import org.om.onestepjob.R
import org.om.onestepjob.databinding.DialogLoadingBinding
import org.om.onestepjob.databinding.IncludeErrorBinding
import org.om.onestepjob.databinding.IncludeSuccessBinding
import org.om.onestepjob.di.AppComponent
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity() {

    abstract fun getViewModelFromChild(): V

    // Do explicit bindings of variables
    abstract fun performExplicitBindings(viewDataBinding: T)

    private lateinit var root: FrameLayout

    // can't name it contentView because of setter
    private lateinit var contentVieww: View
    private lateinit var container: View
    private var viewModel: V? = null
    private var errorViewBinding: IncludeErrorBinding? = null
    private var successViewBinding: IncludeSuccessBinding? = null
    private var loadingDialog: AlertDialog? = null

    var viewDataBinding: T? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = if (viewModel == null) getViewModelFromChild() else viewModel
        subscribeToViewModel()
    }


    override fun setContentView(@LayoutRes contentLayoutResId: Int) {
        super.setContentView(R.layout.base_activity_container)
        root = findViewById(R.id.frame_root)
        container = findViewById(R.id.container)
        viewDataBinding = DataBindingUtil.inflate<T>(
            LayoutInflater.from(this),
            contentLayoutResId, root, false
        )
        performExplicitBindings(viewDataBinding!!)
        viewDataBinding!!.executePendingBindings()
        viewDataBinding!!.lifecycleOwner = this
        contentVieww = viewDataBinding!!.root
        root.addView(contentVieww)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    fun showLoadingDialog(message: String? = null) {
        hideLoadingDialog()
        hideKeyboard()
        val binding = DialogLoadingBinding.inflate(LayoutInflater.from(this), null, false)
        binding.message = message
        loadingDialog = MaterialAlertDialogBuilder(this)
            .setView(binding.root)
            .setCancelable(false)
            .show()
    }

    fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    fun showSnackWithoutAction(
        @StringRes resourceInt: Int, view: View = container,
        length: Int = Snackbar.LENGTH_LONG
    ): Snackbar {
        val snackbar = Snackbar.make(view, resourceInt, length)
        snackbar.show()
        return snackbar
    }

    fun showToast(@StringRes resourceInt: Int, length: Int = Toast.LENGTH_LONG) {
        Toast.makeText(application, resourceInt, length).show()
    }

    fun showToast(string: String, length: Int = Toast.LENGTH_LONG) {
        Toast.makeText(application, string, length).show()
    }

    fun showErrorView(throwable: Throwable, retryAction: () -> Unit) {
        return if (throwable is SocketTimeoutException ||
            throwable is UnknownHostException ||
            throwable is ConnectException
        ) {
            showErrorView(
                getString(R.string.error_title_cannot_connect),
                getString(R.string.error_description_check_internet), retryAction
            )
        }
//        else if (throwable is UserLoggedOutException) {
//            showToast(R.string.msg_logged_out)
//            val intent = Intent(applicationContext, LoginActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            startActivity(intent)
//            finish()
//        }
        else {
            showErrorView(retryAction = retryAction)
        }
    }


    fun showErrorView(
        title: String = getString(R.string.error_oops),
        errorDescription: String = getString(R.string.error_something_went_wrong),
        retryAction: () -> Unit
    ) {
        hideKeyboard()
        if (errorViewBinding == null) {
            errorViewBinding = IncludeErrorBinding.inflate(LayoutInflater.from(this), root, false)
            root.addView(errorViewBinding?.root)
        }
        errorViewBinding?.title = title
        errorViewBinding?.description = errorDescription
        errorViewBinding?.root?.visibility = View.VISIBLE
        errorViewBinding?.animationView?.playAnimation()
        errorViewBinding?.buttonRetry?.setOnClickListener {
            showContentView()
            retryAction.invoke()
        }
        contentVieww.visibility = View.GONE
    }

    fun showSuccessView() {
        hideKeyboard()
        if (successViewBinding == null) {
            successViewBinding =
                IncludeSuccessBinding.inflate(LayoutInflater.from(this), root, false)
            root.addView(successViewBinding?.root)
        }
        successViewBinding?.animationView?.playAnimation()
        contentVieww.visibility = View.GONE
        Handler().postDelayed({
            showContentView()
        }, 3000)
    }

    private fun showContentView() {
        errorViewBinding?.root?.visibility = View.GONE
        successViewBinding?.root?.visibility = View.GONE
        contentVieww.visibility = View.VISIBLE
    }

    private fun subscribeToViewModel() {
        viewModel?.getLoadingLiveData?.observe(this, Observer {
            if (it.first) {
                showLoadingDialog(getString(it.second))
            } else {
                hideLoadingDialog()
            }
        })
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun getAppComponent(): AppComponent {
        return (application as OneStepJobApplication).appComponent
    }

}