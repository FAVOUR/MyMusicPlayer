package com.example.mymusicplayer.data.source.remote.api

import com.example.mymusicplayer.data.source.remote.model.LearnFieldSongs
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface LearnFieldApiService {
    @GET("/data/manifest.json")
    fun fetchSongs(): Single<Response<LearnFieldSongs>>

    @GET
    fun downloadMp3(@Url url:String): Single<Response<LearnFieldSongs>>
}