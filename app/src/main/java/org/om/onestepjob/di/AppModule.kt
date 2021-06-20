package org.om.onestepjob.di

import android.app.Application
import dagger.Module
import dagger.Provides
import org.om.onestepjob.repo.job.HardcodeJobRepository
import org.om.onestepjob.repo.job.JobRepository
import javax.inject.Singleton


@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun providesApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun providesJobRepository(): JobRepository {
        // TODO: Replace with the actual Repository implementation
        return HardcodeJobRepository()
    }
}