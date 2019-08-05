package com.echo.careaxiomtest2.ui.adapter.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.android.careaxiomtest.data.Album
import com.android.careaxiomtest.network.NetworkService
import com.echo.careaxiomtest2.data.model.NetworkState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class PhotoDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : PageKeyedDataSource<Long, Any>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    private var retryCompletable: Completable? = null

    fun retryRequest() {
        retryCompletable?.let {
            compositeDisposable.add(
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                    }, { throwable -> Log.e("ErrorReport", "Error  = " + throwable.message) })
            )
        }
    }

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Any>) {

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        compositeDisposable.add(networkService.getPhotos(1).subscribe({ photos ->
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            // clear retry since last request succeeded
            addRetry(null)
            val listPhotos = addAlbumRow(photos, 1)
            callback.onResult(listPhotos, null, 2)

        }, { throwable ->


            addRetry(Action { loadInitial(params, callback) })

            val error = NetworkState.error(throwable.message)
            networkState.postValue(error)
            initialLoad.postValue(error)
        }))
    }


    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Any>) {
        networkState.postValue(NetworkState.LOADING)

        networkService.getPhotos(params.key).subscribe({ photos ->
            networkState.postValue(NetworkState.LOADED)
            // clear retry since last request succeeded
            addRetry(null)
            val listPhotos = addAlbumRow(photos, params.key.toInt())
            callback.onResult(listPhotos, params.key + 1)

        }, { throwable ->

            Timber.e(throwable.message)
            addRetry(Action { loadAfter(params, callback) })
            networkState.postValue(NetworkState.error(throwable.message))
        })
        // compositeDisposable.add()
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Any>) {

    }

    fun addAlbumRow(list: List<Any>, albumId: Int): List<Any> {
        val result: ArrayList<Any> = ArrayList<Any>()
        result.add(Album(albumId))
        result.addAll(list)
        return result
    }


    private fun addRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)

        }
    }

}