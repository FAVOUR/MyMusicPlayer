package com.example.mymusicplayer.data.source.remote.api

import com.example.mymusicplayer.data.source.remote.model.LearnFieldSongs
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


interface LearnFieldApiService {
    @GET("/Learnfield-GmbH/CodingChallenge/master/react%20native/simple%20audio%20player/data/manifest.json")
    fun fetchSongs(): Single<Response<LearnFieldSongs>>

    @Streaming
    @GET
    fun downloadMp3(@Url url: String): Single<Response<ResponseBody>>
}