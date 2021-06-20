package org.om.onestepjob.di

import dagger.Component
import org.om.onestepjob.ui.home.HomeActivity
import org.om.onestepjob.ui.home.list.ListJobsFragment
import org.om.onestepjob.ui.home.stacked.StackedJobsFragment
import org.om.onestepjob.ui.login.LoginActivity
import org.om.onestepjob.ui.login.otp.VerifyOtpActivity
import org.om.onestepjob.ui.profile.ProfileActivity
import org.om.onestepjob.ui.splash.SplashActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(splashActivity: SplashActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(verifyOtpActivity: VerifyOtpActivity)
    fun inject(profileActivity: ProfileActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(stackedJobsFragment: StackedJobsFragment)
    fun inject(listJobsFragment: ListJobsFragment)
}