package com.example.mymusicplayer.data.source.remote

import com.example.mymusicplayer.data.source.remote.api.LearnFieldApiService
import com.example.mymusicplayer.data.source.remote.model.LearnFieldSongs
import com.example.mymusicplayer.util.TestData.errorResponse
import com.example.mymusicplayer.util.TestData.malFormedErrorResponse
import com.example.mymusicplayer.util.TestData.successResponse
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


internal class RemoteSongsDataSourceImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var learnifiedApiClient: LearnFieldApiService
    private lateinit var retrofitClient: Retrofit
    private lateinit var client: OkHttpClient

    @Before
    fun setUpServer() {
        mockWebServer = MockWebServer()
        client = OkHttpClient.Builder().build()
        retrofitClient = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        learnifiedApiClient = retrofitClient
            .create(LearnFieldApiService::class.java)
    }


    @Test
    fun `success response is not empty and is an instance of LearnFieldSongs`() {
        // Arrange
        val response = MockResponse()
            .setBody(successResponse)
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val remoteSongsDataSource = RemoteSongsDataSourceImpl(learnifiedApiClient,retrofitClient)

        // Act
        val result = remoteSongsDataSource.getSongs()
            .blockingGet()

        // Assert
        assertThat(result).isNotNull()
        assertThat(result).isInstanceOf(LearnFieldSongs::class.java)
    }

    @Test
    fun `success response body is not null and contains a list of 3 songs`() {
        // Arrange
        val response = MockResponse()
            .setBody(successResponse)
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val remoteSongsDataSource = RemoteSongsDataSourceImpl(learnifiedApiClient,retrofitClient)

        // Act
        val result = remoteSongsDataSource.getSongs()
            .blockingGet()

        // Assert
        assertThat(result.songs).isNotNull()
        assertThat(result.songs).hasSize(3)
    }

      @Test
      fun `malformed response returns an empty array`(){
          // Arrange
          val response = MockResponse()
              .setBody(malFormedErrorResponse)
              .setResponseCode(200)

          mockWebServer.enqueue(response)

          val remoteSongsDataSource = RemoteSongsDataSourceImpl(learnifiedApiClient,retrofitClient)

          // Act
          val result = remoteSongsDataSource.getSongs()
              .blockingGet()

          // Assert
          assertThat(result).isEqualTo(LearnFieldSongs(songs = null))
      }

      @Test
      fun `error response returns is not null and returns a single array with an incomplete data`(){
          // Arrange
          val response = MockResponse()
              .setBody(errorResponse)
              .setResponseCode(400)

          mockWebServer.enqueue(response)

          val remoteSongsDataSource = RemoteSongsDataSourceImpl(learnifiedApiClient,retrofitClient)

          // Act
          val result = remoteSongsDataSource.getSongs()
              .blockingGet()

          // Assert
          assertThat(result).isNotNull()
          assertThat(result.songs).hasSize(1)
      }


    @After
    fun shutdownServer() {
        mockWebServer.shutdown()
    }
}