package org.om.onestepjob.ui.home

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.om.onestepjob.R
import org.om.onestepjob.databinding.ActivityHomeBinding
import org.om.onestepjob.ui.ViewModelProviderFactory
import org.om.onestepjob.ui.base.BaseActivity
import org.om.onestepjob.ui.home.list.ListJobsFragment
import org.om.onestepjob.ui.home.stacked.StackedJobsFragment
import javax.inject.Inject

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), HomeNavigator,
    BottomNavigationView.OnNavigationItemSelectedListener {


    @Inject
    lateinit var factory: ViewModelProviderFactory
    private lateinit var viewModelHome: HomeViewModel
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun getViewModelFromChild(): HomeViewModel {
        viewModelHome = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        return viewModelHome
    }

    override fun performExplicitBindings(viewDataBinding: ActivityHomeBinding) {
        viewDataBinding.viewModel = viewModelHome
        bottomNavigationView = viewDataBinding.bottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Please Note the injection has to done before super's on Create
        getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewModelHome.setNavigator(this)
        viewModelHome.fetchJobs()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, StackedJobsFragment())
            .commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_jobs ->
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, StackedJobsFragment())
                    .commit()
            R.id.item_saved_jobs -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.frame_container,
                        ListJobsFragment.getInstance(ListJobsFragment.JobsListType.SAVED)
                    )
                    .commit()
            }
            R.id.item_applied_jobs -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.frame_container,
                        ListJobsFragment.getInstance(ListJobsFragment.JobsListType.APPLIED)
                    )
                    .commit()
            }
        }
        return true
    }


}