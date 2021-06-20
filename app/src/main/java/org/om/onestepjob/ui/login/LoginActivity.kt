package org.om.onestepjob.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hbb20.CountryCodePicker
import org.om.onestepjob.R
import org.om.onestepjob.databinding.ActivityLoginBinding
import org.om.onestepjob.ui.ViewModelProviderFactory
import org.om.onestepjob.ui.base.BaseActivity
import org.om.onestepjob.ui.login.otp.VerifyOtpActivity
import org.om.onestepjob.ui.profile.ProfileActivity
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {



    @Inject
    lateinit var factory: ViewModelProviderFactory
    companion object{
        const val REQUEST_CODE_VERIFY_OTP = 1221
    }
    private lateinit var viewModelLogin: LoginViewModel
    private lateinit var buttonLogin: MaterialButton
    private lateinit var ccp: CountryCodePicker
    private lateinit var editTextMobileNo: TextInputEditText
    private lateinit var inputLayoutMobileNo: TextInputLayout


    override fun getViewModelFromChild(): LoginViewModel {
        viewModelLogin = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        return viewModelLogin
    }

    override fun performExplicitBindings(viewDataBinding: ActivityLoginBinding) {
        viewDataBinding.viewModel = viewModelLogin
        buttonLogin = viewDataBinding.buttonLoginOtp
        ccp = viewDataBinding.ccp
        editTextMobileNo = viewDataBinding.editMobile
        inputLayoutMobileNo = viewDataBinding.inputMobile
        configureCcp()
        buttonLogin.setOnClickListener { generateOtp() }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        // Please Note the injection has to done before super's on Create
        getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModelLogin.setNavigator(this)
    }

    override fun openVerifyOtpActivity() {
        showToast(R.string.snack_otp_sent)
        startActivityForResult(
            VerifyOtpActivity.getStartIntent(
                this,
                ccp.fullNumber.substringAfter(ccp.selectedCountryCode)
            ),
            REQUEST_CODE_VERIFY_OTP
        )
    }

    override fun openProfile() {
        startActivity(Intent(this, ProfileActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_VERIFY_OTP && resultCode == Activity.RESULT_OK) {
            if (data?.getBooleanExtra(VerifyOtpActivity.EXTRA_RESULT_LOGIN_SUCCESS, false) == true) {
                openProfile()
            } else if (data?.getBooleanExtra(VerifyOtpActivity.EXTRA_RESULT_RESEND_OTP, false) == true) {
                generateOtp()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun generateOtp() {
        if (!ccp.isValidFullNumber) {
            inputLayoutMobileNo.error = getString(R.string.login_mobile_no_error)
            return
        }

        // Send request to generate otp via viewmodel
        viewModelLogin.generateOtp()

    }

    private fun configureCcp() {
        ccp.registerCarrierNumberEditText(editTextMobileNo)
        ccp.setPhoneNumberValidityChangeListener {
            if (it) {
                inputLayoutMobileNo.error = null
            } else if (!editTextMobileNo.text.isNullOrBlank()) {
                inputLayoutMobileNo.error = getString(R.string.login_mobile_no_error)
            }
        }
    }
}