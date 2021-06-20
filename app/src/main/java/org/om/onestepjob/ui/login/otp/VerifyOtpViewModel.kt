package org.om.onestepjob.ui.login.otp

import org.om.onestepjob.repo.job.JobRepository
import org.om.onestepjob.ui.base.BaseViewModel

class VerifyOtpViewModel(private val jobRepository: JobRepository) :
    BaseViewModel<VerifyOtpNavigator>() {
    var mobileNumber: String? = null
    var enteredPin: String? = null

    fun verifyOtp() {
        getNavigator()?.onSuccessfulLogin()
    }

    fun resendOtp() {
        getNavigator()?.resendOtp()
    }
}