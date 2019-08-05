package com.android.careaxiomtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.echo.careaxiomtest2.data.model.NetworkState
import com.echo.careaxiomtest2.ui.adapter.paging.PhotoDataSource
import com.echo.careaxiomtest2.ui.adapter.paging.PhotoDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class MainActivityVM : ViewModel() {

    var photos: LiveData<PagedList<Any>>

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val pageSize = 8
    private val sourceFactory: PhotoDataSourceFactory


    init {
        sourceFactory =
                PhotoDataSourceFactory(compositeDisposable)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        photos = LivePagedListBuilder<Long, Any>(sourceFactory, config).build()
    }


    fun retryRequest() {
        sourceFactory.photoDSLive.value?.retryRequest()
    }


    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<PhotoDataSource, NetworkState>(
        sourceFactory.photoDSLive, { it.networkState })

    override fun onCleared() {
        super.onCleared()
        if (compositeDisposable != null) {
            compositeDisposable.dispose()
        }
    }

}

