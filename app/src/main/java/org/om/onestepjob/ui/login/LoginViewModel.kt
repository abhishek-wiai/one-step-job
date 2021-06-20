package org.om.onestepjob.ui.login

import org.om.onestepjob.repo.job.JobRepository
import org.om.onestepjob.ui.base.BaseViewModel

class LoginViewModel(private val jobRepository: JobRepository) : BaseViewModel<LoginNavigator>() {

    fun generateOtp(){
        getNavigator()?.openVerifyOtpActivity()
    }

    fun openProfile(){
        getNavigator()?.openProfile()
    }

}