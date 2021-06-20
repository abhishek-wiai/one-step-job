package org.om.onestepjob.ui.home.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.om.onestepjob.R
import org.om.onestepjob.databinding.FragmentListJobsBinding
import org.om.onestepjob.ui.ViewModelProviderFactory
import org.om.onestepjob.ui.base.BaseFragment
import org.om.onestepjob.ui.home.HomeViewModel
import javax.inject.Inject

class ListJobsFragment : BaseFragment<FragmentListJobsBinding, HomeViewModel>() {
    @Inject
    lateinit var factory: ViewModelProviderFactory

    enum class JobsListType {
        SAVED,
        APPLIED
    }

    companion object {
        const val KEY_LIST_TYPE = "ListJobsFragment.ListType"
        fun getInstance(listType: JobsListType): ListJobsFragment {
            val bundle = Bundle()
            bundle.putInt(KEY_LIST_TYPE, listType.ordinal)
            val fragment = ListJobsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var listType: JobsListType;

    private lateinit var adapter: ListJobsAdapter


    private lateinit var viewModelHome: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: View

    override val layoutId: Int
        get() = R.layout.fragment_list_jobs


    override fun getViewModelFromChild(): HomeViewModel {
        viewModelHome =
            ViewModelProviders.of(baseActivity!!, factory).get(HomeViewModel::class.java)
        return viewModelHome
    }

    override fun performExplicitBindings(viewDataBinding: FragmentListJobsBinding) {
        viewDataBinding.viewModel = viewModelHome
        recyclerView = viewDataBinding.recyclerViewJobs
        emptyView = viewDataBinding.emptyView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getAppComponent()?.inject(this)
        listType = JobsListType.values()[arguments?.getInt(KEY_LIST_TYPE)!!]
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUiElements()
    }

    private fun setUpUiElements() {
        if (listType == JobsListType.SAVED) {
            subscribeToSavedJobs()
        } else if (listType == JobsListType.APPLIED) {
            subscribeToAppliedJobs()
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun subscribeToAppliedJobs() {
        viewModelHome.appliedJobs.observe(viewLifecycleOwner, Observer {
            adapter = ListJobsAdapter(it)
            recyclerView.adapter = adapter
            refreshEmptyView()
        })
    }

    private fun subscribeToSavedJobs() {
        viewModelHome.savedJobs.observe(viewLifecycleOwner, Observer {
            adapter = ListJobsAdapter(it)
            recyclerView.adapter = adapter
            refreshEmptyView()
        })
    }

    private fun refreshEmptyView() {
        if (adapter.itemCount == 0) {
            emptyView.visibility = View.VISIBLE
        }
    }


}