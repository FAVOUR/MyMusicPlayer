package com.example.mymusicplayer.data.repository

import com.example.mymusicplayer.data.repository.model.Result
import com.example.mymusicplayer.data.source.local.entity.Song
import io.reactivex.Maybe
import io.reactivex.Observable

interface SongRepository {
    fun obtainSongs(): Observable<Result<List<Song>>>
    fun downloadSpecificSong(specificSong: Song): Observable<Result<Song>>
    fun storeSongsCacheSongs(song: Song): Maybe<Result<Long>>
    fun observeSpecificSong(songTitle: String): Observable<Result<Song>>
}