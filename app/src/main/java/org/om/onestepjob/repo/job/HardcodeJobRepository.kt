package org.om.onestepjob.repo.job

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.om.onestepjob.model.Job
import java.util.concurrent.TimeUnit

class HardcodeJobRepository : JobRepository {
    // Fake delay to give impression of API Call
    val FAKE_DELAY_MILLIS = 3000L


    private lateinit var jobs: List<Job>
    private val subjectJobs = BehaviorSubject.create<List<Job>>()

    override fun fetchJobs(): Completable {
        return Completable.create {
            jobs = populateJobs()
            subjectJobs.onNext(jobs)
            it.onComplete()
        }.delay(FAKE_DELAY_MILLIS, TimeUnit.MILLISECONDS)

    }

    override fun getJobs(): Observable<List<Job>> {
        return subjectJobs
    }




    override fun markJobSaved(jobId: Int): Completable {
        return Completable.create { com ->
            jobs.first { it.id == jobId }.let { it.saved = true
                it.seen = true
            }
            subjectJobs.onNext(jobs)
            com.onComplete()
        }
    }

    override fun markJobApplied(jobId: Int): Completable {
        return Completable.create { com ->
            jobs.first { it.id == jobId }.let {
                it. applied = true
                it.seen = true
            }
                subjectJobs.onNext(jobs)
                com.onComplete()
            }
    }


    override fun markJobSeen(jobId: Int): Completable {
        return Completable.create { com ->
            jobs.first { it.id == jobId }.seen = true
            subjectJobs.onNext(jobs)
            com.onComplete()
        }
    }


    private fun populateJobs(): List<Job> {
        val jobs = mutableListOf<Job>()
        jobs.add(Job(1, "Full Stack Engineer", "Wonderful Inc", "Mumbai", "INR 1000000", "2-3 Years"))
        jobs.add(Job(2, "Android Engineer", "Awesome Pvt Ltd", "Mumbai", "INR 1200000", "2+ Years"))
        jobs.add(Job(3, "Django Developer", "Great Place", "Delhi", "INR 1200000", "3+ Years"))
        jobs.add(Job(4, "Html Developer", "Cool Company", "Mumbai", "INR 1100000", "4+ Years"))
        jobs.add(Job(5, "Full Stack Ninja", "Wow Corp", "Mumbai", "INR 1000000", "1+ Years"))
        jobs.add(Job(6, "Front End Dev", "BBB", "Delhi", "INR 2000000", "0+ Years"))
        return jobs
    }
}