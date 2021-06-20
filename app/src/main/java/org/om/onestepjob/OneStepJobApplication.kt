package org.om.onestepjob

import android.app.Application
import org.om.onestepjob.di.AppComponent
import org.om.onestepjob.di.AppModule
import org.om.onestepjob.di.DaggerAppComponent
import timber.log.Timber

class OneStepJobApplication : Application() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appInstance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(appInstance))
            .build()
    }

    companion object {
        lateinit var appInstance: OneStepJobApplication
            private set
    }
}