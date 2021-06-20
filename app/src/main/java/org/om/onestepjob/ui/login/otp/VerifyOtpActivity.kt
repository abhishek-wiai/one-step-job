package org.om.onestepjob.ui.login.otp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import org.om.onestepjob.R
import org.om.onestepjob.databinding.ActivityVerifyOtpBinding
import org.om.onestepjob.ui.ViewModelProviderFactory
import org.om.onestepjob.ui.base.BaseActivity
import org.om.onestepjob.util.onTextChanged
import javax.inject.Inject

class VerifyOtpActivity : BaseActivity<ActivityVerifyOtpBinding, VerifyOtpViewModel>(),
    VerifyOtpNavigator {
    @Inject
    lateinit var factory: ViewModelProviderFactory

    companion object {
        private const val EXTRA_MOBILE_NO = "VerifyOtpActivity.MobileNo"
        const val EXTRA_RESULT_LOGIN_SUCCESS = "VerifyOtpActivity.LoginSuccess"
        const val EXTRA_RESULT_RESEND_OTP = "VerifyOtpActivity.ResendOtp"

        fun getStartIntent(context: Context, mobileNo: String): Intent {
            return Intent(context, VerifyOtpActivity::class.java).apply {
                putExtra(EXTRA_MOBILE_NO, mobileNo)
            }
        }
    }

    private lateinit var viewModelVerifyOtp: VerifyOtpViewModel
    private lateinit var toolbar: MaterialToolbar
    private lateinit var editTextOtp: PinEntryEditText
    private lateinit var buttonVerifyAndLogin: MaterialButton
    private lateinit var buttonResend: MaterialButton


    override fun getViewModelFromChild(): VerifyOtpViewModel {
        viewModelVerifyOtp = ViewModelProviders.of(this, factory)
            .get(VerifyOtpViewModel::class.java)
        viewModelVerifyOtp.mobileNumber = intent.getStringExtra(EXTRA_MOBILE_NO)
        return viewModelVerifyOtp
    }

    override fun performExplicitBindings(viewDataBinding: ActivityVerifyOtpBinding) {
        viewDataBinding.viewModel = viewModelVerifyOtp
        editTextOtp = viewDataBinding.editOtp
        toolbar = viewDataBinding.toolbar
        buttonVerifyAndLogin = viewDataBinding.buttonVerifyOtp
        buttonResend = viewDataBinding.buttonResend
        configureToolBar()
        configurePinView()
    }

    override fun onSuccessfulLogin() {
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_RESULT_LOGIN_SUCCESS, true)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun resendOtp() {
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_RESULT_RESEND_OTP, true)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)
        viewModelVerifyOtp.setNavigator(this)
    }

    private fun configureToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun configurePinView() {
        editTextOtp.setMaxLength(resources.getInteger(R.integer.otp_length))
        editTextOtp.onTextChanged {
            buttonVerifyAndLogin.isEnabled = editTextOtp.text?.length == resources.getInteger(R.integer.otp_length)
        }
    }
}