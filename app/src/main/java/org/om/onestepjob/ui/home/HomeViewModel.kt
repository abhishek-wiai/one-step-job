package org.om.onestepjob.ui.home

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.om.onestepjob.R
import org.om.onestepjob.model.Job
import org.om.onestepjob.repo.job.JobRepository
import org.om.onestepjob.ui.base.BaseViewModel


class HomeViewModel(private val jobRepository: JobRepository) : BaseViewModel<HomeNavigator>() {


    private val _allJobs: MutableLiveData<List<Job>> = MutableLiveData()

    // Show jobs which are not seen yet
    val unseenJobs =
        Transformations.map(_allJobs) { jobs: List<Job> -> jobs.filter { !it.seen } }
    val appliedJobs =
        Transformations.map(_allJobs) { jobs: List<Job> -> jobs.filter { it.applied } }
    val savedJobs = Transformations.map(_allJobs) { jobs: List<Job> -> jobs.filter { it.saved } }

    fun fetchJobs() {
        setLoading(true, R.string.msg_loading_jobs)
        compositeDisposable.add(
            jobRepository.fetchJobs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setLoading(false)
                    subscribeToJobs()
                },
                    {
                        setLoading(false)
                        // TODO: Handle errors later
                    }
                )
        )
    }

    private fun subscribeToJobs() {
        compositeDisposable.add(jobRepository.getJobs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
               _allJobs.value = it
            },
                {
                    // TODO: Handle errors later
                }
            ))
    }


    fun markJobSaved(id: Int) {
        compositeDisposable.add(jobRepository.markJobSaved(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // Do Nothing
            },
                {
                    // TODO: Handle errors later
                }
            ))
    }

    fun markJobApplied(id: Int) {
        compositeDisposable.add(jobRepository.markJobApplied(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // Do Nothing
            },
                {
                    // TODO: Handle errors later
                }
            ))
    }

    fun markJobSeen(id: Int) {
        compositeDisposable.add(jobRepository.markJobSeen(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // Do Nothing
            },
                {
                    // TODO: Handle errors later
                }
            ))
    }
}