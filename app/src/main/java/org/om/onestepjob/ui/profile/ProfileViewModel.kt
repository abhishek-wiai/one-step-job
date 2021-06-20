package org.om.onestepjob.ui.profile

import org.om.onestepjob.repo.job.JobRepository
import org.om.onestepjob.ui.base.BaseViewModel

class ProfileViewModel (private val jobRepository: JobRepository) : BaseViewModel<ProfileNavigator>() {
}