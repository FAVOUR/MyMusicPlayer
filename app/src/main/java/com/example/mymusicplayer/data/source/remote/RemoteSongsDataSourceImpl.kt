package com.example.mymusicplayer.data.source.remote

import com.example.mymusicplayer.data.source.remote.api.LearnFieldApiService
import com.example.mymusicplayer.data.source.remote.model.LearnFieldSongs
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject


class RemoteSongsDataSourceImpl @Inject constructor(
    private val learnifiedApiClient: LearnFieldApiService,
    private val retrofitClient: Retrofit,
) : RemoteSongsDataSource {

    override fun getSongs(): Single<LearnFieldSongs> {
        return learnifiedApiClient.fetchSongs().extractData(retrofitClient)
    }

    override fun downloadMp3(): Single<LearnFieldSongs> {
        return learnifiedApiClient.fetchSongs().extractData(retrofitClient)
    }

}


inline fun <reified T> Single<Response<T>>.extractData(retrofitClient: Retrofit): Single<T> {
    return this.map {
            if (it.isSuccessful) {
                it.body()
            } else {
                val converter =
                    retrofitClient.responseBodyConverter<T>(T::class.java, arrayOfNulls(0))
                converter.convert(it.errorBody())
            }
        }

}


