package com.example.mymusicplayer.di

import android.content.Context
import com.example.mymusicplayer.data.source.local.LocalSongDataSource
import com.example.mymusicplayer.data.source.local.LocalSongDataSourceImpl
import com.example.mymusicplayer.data.source.remote.RemoteSongsDataSource
import com.example.mymusicplayer.data.source.remote.RemoteSongsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideContext(@ApplicationContext context: Context): Context

    @Binds
    abstract fun bindRemoteSongsDataSource(impl: RemoteSongsDataSourceImpl): RemoteSongsDataSource

    @Binds
    abstract fun bindALocalSongDataSource(impl: LocalSongDataSourceImpl): LocalSongDataSource
}