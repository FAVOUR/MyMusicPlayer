package com.example.mymusicplayer.data.source.remote

import com.example.mymusicplayer.data.source.remote.model.LearnFieldSongs
import io.reactivex.Single

interface RemoteSongsDataSource {
    fun getSongs(): Single<LearnFieldSongs>
    fun downloadMp3(): Single<LearnFieldSongs>
}