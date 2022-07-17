package com.example.mymusicplayer.di

import android.content.Context
import androidx.room.Room
import com.example.mymusicplayer.data.source.local.dao.SongDao
import com.example.mymusicplayer.data.source.local.db.SongDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): SongDataBase {
        return Room.databaseBuilder(
            context,
            SongDataBase::class.java, "song_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSongDao(songDataBase: SongDataBase): SongDao {
        return songDataBase.songDao()
    }
}