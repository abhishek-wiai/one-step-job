package org.om.onestepjob.ui.splash

import org.om.onestepjob.repo.job.JobRepository
import org.om.onestepjob.ui.base.BaseViewModel

class SplashViewModel(private val jobRepository: JobRepository) : BaseViewModel<SplashNavigator>() {

    fun selectNextActivity() {
            // Here we can choose which activity to be launched, for now launching
        // the login activity only

        getNavigator()?.openLoginActivity()
    }
}