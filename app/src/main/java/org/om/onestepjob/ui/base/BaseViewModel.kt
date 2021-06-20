package org.om.onestepjob.ui.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.om.onestepjob.R
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>() : ViewModel() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _isLoading: MutableLiveData<Pair<Boolean, Int>> =
        MutableLiveData<Pair<Boolean, Int>>().apply { Pair(false, 0) }
    private var _navigator: WeakReference<N>? = null

    val getLoadingLiveData: LiveData<Pair<Boolean, Int>>
        get() {
            return _isLoading
        }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    protected fun setLoading(
        showLoading: Boolean,
        @StringRes stringRes: Int = R.string.msg_dialog_loading
    ) {
        _isLoading.value = Pair(showLoading, stringRes)
    }

    fun getNavigator(): N? {
        return _navigator?.get()
    }

    fun setNavigator(navigator: N) {
        this._navigator = WeakReference(navigator)
    }


}