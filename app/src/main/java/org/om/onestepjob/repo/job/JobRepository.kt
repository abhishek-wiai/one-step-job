package org.om.onestepjob.repo.job

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.om.onestepjob.model.Job


interface JobRepository {


    fun fetchJobs(): Completable
    fun getJobs(): Observable<List<Job>>
    fun markJobSaved(jobId: Int): Completable
    fun markJobApplied(jobId: Int): Completable
    fun markJobSeen(jobId: Int): Completable
}