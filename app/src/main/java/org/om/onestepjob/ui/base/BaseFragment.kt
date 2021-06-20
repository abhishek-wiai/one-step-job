package org.om.onestepjob.ui.base


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.om.onestepjob.di.AppComponent

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment() {

    /**
     *  layout resource id
     *  The child will have to write the getter for it
     */
    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun getViewModelFromChild(): V

    // Do explicit bindings of variables
    abstract fun performExplicitBindings(viewDataBinding: T)

    private var viewModel: V? = null
    var baseActivity: BaseActivity<*, *>? = null
    private var rootView: View? = null
    private var viewDataBinding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = if (viewModel == null) getViewModelFromChild() else viewModel
        subscribeToViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.baseActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        rootView = viewDataBinding!!.root
        return rootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    private fun subscribeToViewModel() {
        viewModel?.getLoadingLiveData?.observe(this, Observer {
            if (it.first) {
                baseActivity?.showLoadingDialog(getString(it.second))
            } else {
                baseActivity?.hideLoadingDialog()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.lifecycleOwner = this
        performExplicitBindings(viewDataBinding!!)
        viewDataBinding!!.executePendingBindings()
    }


    fun hideKeyboard() {
        baseActivity?.hideKeyboard()
    }

    fun getAppComponent(): AppComponent? {
        return baseActivity?.getAppComponent()
    }

    fun clearBackStack() {
        for (i in 0 until fragmentManager?.backStackEntryCount!!) {
            fragmentManager?.popBackStack()
        }
    }
}