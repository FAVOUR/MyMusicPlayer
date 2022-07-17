package com.example.mymusicplayer.ui.util

import androidx.lifecycle.MutableLiveData
import com.example.mymusicplayer.ui.SongsUiState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.standard(
    liveObj: MutableLiveData<SongsUiState>? = null, //make it more Scaleable
    failedDataState: SongsUiState? = null  //make it more scaleable
): Observable<T> {

    return this
        .doOnError {
            failedDataState?.let { liveObj?.postValue(it) }
                ?: kotlin.run {
                    liveObj?.postValue(SongsUiState.Error(Exception(it.message)))
                }
        }
        .onErrorResumeNext(Observable.empty())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


}