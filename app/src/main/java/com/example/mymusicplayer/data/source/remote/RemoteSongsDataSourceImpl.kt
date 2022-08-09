package com.example.mymusicplayer.data.source.remote

import com.example.mymusicplayer.data.source.remote.api.LearnFieldApiService
import com.example.mymusicplayer.data.source.remote.model.LearnFieldSongs
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject


class RemoteSongsDataSourceImpl @Inject constructor(
    private val learnifiedApiClient: LearnFieldApiService,
    private val retrofitClient: Retrofit,
) : RemoteSongsDataSource {

    override fun getSongs(): Observable<LearnFieldSongs> {
        return learnifiedApiClient.fetchSongs().extractData(retrofitClient).toObservable()
    }

    override fun downloadAudio(url: String): Observable<ResponseBody> {
        return learnifiedApiClient.downloadMp3(url = url).extractData(retrofitClient).toObservable()
    }

}

inline fun <reified T> Single<Response<T>>.extractData(retrofitClient: Retrofit): Single<T> {
    return this.map {
        if (it.isSuccessful) {
            it.body()
        } else {
            val converter =
                retrofitClient.responseBodyConverter<T>(T::class.java, arrayOfNulls(0))
            converter.convert(it.errorBody()) //Breaks the ci build
        }
    }

}


