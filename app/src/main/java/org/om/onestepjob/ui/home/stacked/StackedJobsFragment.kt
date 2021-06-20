package org.om.onestepjob.ui.home.stacked

import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yuyakaido.android.cardstackview.*
import org.om.onestepjob.R
import org.om.onestepjob.databinding.FragmentStackedJobsBinding
import org.om.onestepjob.ui.ViewModelProviderFactory
import org.om.onestepjob.ui.base.BaseFragment
import org.om.onestepjob.ui.home.HomeViewModel
import timber.log.Timber
import javax.inject.Inject

class StackedJobsFragment : BaseFragment<FragmentStackedJobsBinding, HomeViewModel>(),
    CardStackListener {
    @Inject
    lateinit var factory: ViewModelProviderFactory
    private lateinit var viewModelHome: HomeViewModel
    private lateinit var cardStackView: CardStackView
    private lateinit var adapter: StackedJobsAdapter
    private lateinit var manager: CardStackLayoutManager
    private lateinit var emptyView: View


    override val layoutId: Int
        get() = R.layout.fragment_stacked_jobs

    override fun getViewModelFromChild(): HomeViewModel {
        viewModelHome =
            ViewModelProviders.of(baseActivity!!, factory).get(HomeViewModel::class.java)
        return viewModelHome
    }

    override fun performExplicitBindings(viewDataBinding: FragmentStackedJobsBinding) {
        viewDataBinding.viewModel = viewModelHome
        cardStackView = viewDataBinding.cardStackView
        emptyView = viewDataBinding.emptyView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getAppComponent()?.inject(this)
        super.onCreate(savedInstanceState)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("View Created")
        subscribeToJobs()
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Timber.d("Card Dissapeared $position")
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Timber.d("Card Dragging%s", ratio.toString())

    }

    override fun onCardSwiped(direction: Direction?) {
        Timber.d("Pos:${manager.topPosition}")
        if (direction == Direction.Top) {
            baseActivity?.showToast(getString(R.string.msg_job_applied))
            viewModelHome.markJobApplied(adapter.getJobsList()[manager.topPosition - 1].id)
        } else if (direction == Direction.Bottom) {
            baseActivity?.showToast(getString(R.string.msg_job_saved))
            viewModelHome.markJobSaved(adapter.getJobsList()[manager.topPosition - 1].id)
        }else{
            viewModelHome.markJobSeen(adapter.getJobsList()[manager.topPosition - 1].id)
        }
    }

    override fun onCardCanceled() {
        Timber.d("Card Canceled")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Timber.d("Card Appear $position")
    }

    override fun onCardRewound() {
        Timber.d("Card Rewound")
    }

    private fun subscribeToJobs() {
        viewModelHome.unseenJobs.observe(viewLifecycleOwner, Observer{
            Timber.d("No.of Jobs:${it.size}")
            adapter = StackedJobsAdapter(it)
            setUpUiElements()
            cardStackView.adapter = adapter
            refreshEmptyView()
        })
    }

    private fun setUpUiElements() {
        manager = CardStackLayoutManager(baseActivity, this)
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.FREEDOM)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
    }

    private fun refreshEmptyView() {
        if (adapter.itemCount == 0 || manager.topView == null) {
            emptyView.visibility = View.VISIBLE
        }
    }
}