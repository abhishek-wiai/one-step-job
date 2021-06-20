package org.om.onestepjob.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.om.onestepjob.repo.job.JobRepository
import org.om.onestepjob.ui.home.HomeViewModel
import org.om.onestepjob.ui.login.LoginViewModel
import org.om.onestepjob.ui.login.otp.VerifyOtpViewModel
import org.om.onestepjob.ui.profile.ProfileViewModel
import org.om.onestepjob.ui.splash.SplashViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory @Inject constructor(
    private val jobRepository: JobRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(
                jobRepository
            ) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(
                jobRepository
            ) as T
            modelClass.isAssignableFrom(VerifyOtpViewModel::class.java) -> VerifyOtpViewModel(
                jobRepository
            ) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(
                jobRepository
            ) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(
                jobRepository
            ) as T
            else -> throw  IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

}