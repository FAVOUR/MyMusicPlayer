package com.example.mymusicplayer.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.example.mymusicplayer.data.source.local.entity.Song
import io.reactivex.Maybe
import io.reactivex.Single


@Dao
interface SongDao {
    @Insert(onConflict = REPLACE)
    fun storeSong(song: Song): Maybe<Long>

    @Query("SELECT * FROM song")
    fun fetchAllSong(): Single<Song>

    @Query("SELECT * FROM song where title =:title")
    fun obtainSongByTitle(title: String): Single<Song>

    @Query("SELECT * FROM song where isFavourite =:isFavourite")
    fun getFavouriteSong(isFavourite: Boolean = true): Song

    @Transaction
    fun updateFavouriteSong(favouriteSong: Song){
        return getFavouriteSong().run {
            storeSong(this.copy(isFavourite = false))
            storeSong(favouriteSong)
        }
    }
}