package com.echo.careaxiomtest2.ui.adapter.paging

import androidx.paging.PageKeyedDataSource
import androidx.lifecycle.MutableLiveData
import com.android.careaxiomtest.data.Photo
import com.android.careaxiomtest.network.NetworkService
import com.echo.careaxiomtest2.data.model.NetworkState
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable


class PhotoDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : PageKeyedDataSource<Long, Photo>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Photo>) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        compositeDisposable.add(networkService.getPhotos(1).subscribe({ photos ->
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(photos, null, 2)

        }, { throwable ->

            // setRetry(Action { loadInitial(params, callback) })
            val error = NetworkState.error(throwable.message)
            networkState.postValue(error)
            initialLoad.postValue(error)
        }))
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Photo>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(networkService.getPhotos(params.key).subscribe({ photos ->
            networkState.postValue(NetworkState.LOADED)
            callback.onResult(photos, params.key + 1)

        }, { throwable ->
            networkState.postValue(NetworkState.error(throwable.message))
        }))
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Photo>) {

    }

}