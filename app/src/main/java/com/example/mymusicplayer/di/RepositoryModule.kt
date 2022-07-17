package com.example.mymusicplayer.di

import com.example.mymusicplayer.data.repository.SongRepository
import com.example.mymusicplayer.data.repository.SongRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSongRepository(impl: SongRepositoryImpl): SongRepository
}