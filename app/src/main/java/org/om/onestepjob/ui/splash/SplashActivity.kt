package org.om.onestepjob.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProviders
import org.om.onestepjob.R
import org.om.onestepjob.databinding.ActivitySplashBinding
import org.om.onestepjob.ui.ViewModelProviderFactory
import org.om.onestepjob.ui.base.BaseActivity
import org.om.onestepjob.ui.login.LoginActivity
import javax.inject.Inject

const val SPLASH_SCREEN_INTERVAL: Long = 2000

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {
    @Inject
    lateinit var factory: ViewModelProviderFactory
    private lateinit var viewModelSplash: SplashViewModel

    override fun getViewModelFromChild(): SplashViewModel {
        viewModelSplash = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        return viewModelSplash
    }

    override fun openLoginActivity() {

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun openHomeActivity() {
        finish()
    }

    override fun performExplicitBindings(viewDataBinding: ActivitySplashBinding) {
        viewDataBinding.viewModel = viewModelSplash
    }

    private var backPressed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        // Please Note the injection has to done before super's on Create
        getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModelSplash.setNavigator(this)
        Handler().postDelayed({
            if(!backPressed) {
                    viewModelSplash.selectNextActivity()
            }
        }, SPLASH_SCREEN_INTERVAL)


    }


    override fun onBackPressed() {
        backPressed = true
        super.onBackPressed()
    }
}