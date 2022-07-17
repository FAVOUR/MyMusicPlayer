package com.example.mymusicplayer.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymusicplayer.data.source.local.dao.SongDao

@Database(entities =[SongDataBase::class],version = 1)
abstract class SongDataBase:RoomDatabase() {

    abstract fun songDao():SongDao
}