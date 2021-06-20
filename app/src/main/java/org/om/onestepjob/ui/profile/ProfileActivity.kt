package org.om.onestepjob.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.om.onestepjob.R
import org.om.onestepjob.databinding.ActivityProfileBinding
import org.om.onestepjob.ui.ViewModelProviderFactory
import org.om.onestepjob.ui.base.BaseActivity
import org.om.onestepjob.ui.home.HomeActivity
import org.om.onestepjob.util.onTextChanged
import javax.inject.Inject

class ProfileActivity : BaseActivity<ActivityProfileBinding, ProfileViewModel>(), ProfileNavigator  {
    @Inject
    lateinit var factory: ViewModelProviderFactory
    private  lateinit var viewModelProfile: ProfileViewModel

    private lateinit var editTextTellUs: TextInputEditText
    private lateinit var buttonGetMeJobs: MaterialButton
    override fun getViewModelFromChild(): ProfileViewModel {
        viewModelProfile = ViewModelProviders.of(this, factory).get(ProfileViewModel::class.java)
        return viewModelProfile
    }

    override fun performExplicitBindings(viewDataBinding: ActivityProfileBinding) {
       viewDataBinding.viewModel = viewModelProfile
        buttonGetMeJobs = viewDataBinding.buttonGetJobs
        editTextTellUs =  viewDataBinding.editTellUs
        configureEditTellUs()
        buttonGetMeJobs.setOnClickListener {
            hideKeyboard()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Please Note the injection has to done before super's on Create
        getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        viewModelProfile.setNavigator(this)
    }

    private fun configureEditTellUs() {
        editTextTellUs.onTextChanged {
            buttonGetMeJobs.isEnabled = (editTextTellUs.text?.length) ?: 0>= resources.getInteger(R.integer.tell_us_min_length)
                    && (editTextTellUs.text?.length) ?: 0 <= resources.getInteger(R.integer.tell_us_max_length)
        }
    }
}