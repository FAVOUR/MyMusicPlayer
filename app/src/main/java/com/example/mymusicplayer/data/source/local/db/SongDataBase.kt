package com.example.mymusicplayer.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymusicplayer.data.source.local.dao.SongDao
import com.example.mymusicplayer.data.source.local.entity.Song

@Database(entities = [Song::class], version = 1)
abstract class SongDataBase : RoomDatabase() {

    abstract fun songDao(): SongDao
}