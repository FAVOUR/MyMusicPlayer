package com.example.mymusicplayer.di

import android.content.Context
import com.example.mymusicplayer.BuildConfig
import com.example.mymusicplayer.data.source.remote.api.LearnFieldApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.mymusicplayer.di.annotation.BaseUrl
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val HTTP_CACHE_SIZE = 1024 * 1024 * 1024L
    private const val CONNECTION_TIMEOUT_SECONDS = 600L

    @Provides
    @BaseUrl
    fun provideBaseUrl(): HttpUrl = BuildConfig.API_URL.toHttpUrl()

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheDirName = "okhttp_cache"

        val cacheDirectory = context.getDir(cacheDirName, Context.MODE_PRIVATE)
        return Cache(cacheDirectory, HTTP_CACHE_SIZE)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, @BaseUrl httpBaseUrl: HttpUrl): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(httpBaseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): LearnFieldApiService {
        return retrofit.create(LearnFieldApiService::class.java)
    }
}