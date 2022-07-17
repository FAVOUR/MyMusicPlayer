package com.example.mymusicplayer.ui.util

import androidx.lifecycle.MutableLiveData
import com.example.mymusicplayer.data.repository.model.Result
import com.example.mymusicplayer.ui.HomeUiState
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalArgumentException

fun <T> Observable<T>.standard(
    liveObj: MutableLiveData<HomeUiState>? = null, //make it more Scalable
    failedDataState: HomeUiState? = null  //make it more scalable
): Observable<T> {

    return this
        .doOnError {
            failedDataState?.let { liveObj?.postValue(it) }
                ?: kotlin.run {
                    liveObj?.postValue(HomeUiState.Error(Exception(it.message)))
                }
        }
        .onErrorResumeNext(Observable.empty())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


}



fun <T : Any> Observable<T>.createResult(
    success: (T) -> Result<T>,
    failure: (Exception) -> Result<T>,
): Observable<Result<T>> {
    return try {
        map {
            if (it != null) {
                success(it)
            } else {
                failure(IllegalArgumentException("Response Is empty"))//Do not hardcode
            }
        }
    } catch (e: Throwable) {
        Observable.just(Result.Error(java.lang.Exception("An Error Occurred")))//Do not hardcode
    }
}

fun <T : Any> Maybe<T>.createResult(
    success: (T) -> Result<T>,
    failure: (Exception) -> Result<T>,
): Maybe<Result<T>> {
    return try {
        map {
            if (it != null) {
                success(it)
            } else {
                failure(IllegalArgumentException("Response Is empty")) //Do not hardcode
            }
        }
    } catch (e: Throwable) {
        Maybe.just(Result.Error(java.lang.Exception("An Error Occurred")))//Do not hardcode
    }
}