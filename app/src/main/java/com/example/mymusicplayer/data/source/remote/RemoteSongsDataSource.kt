package com.example.mymusicplayer.data.source.remote

import com.example.mymusicplayer.data.source.remote.model.LearnFieldSongs
import io.reactivex.Observable
import okhttp3.ResponseBody

interface RemoteSongsDataSource {
    fun getSongs(): Observable<LearnFieldSongs>
    fun downloadAudio(url: String): Observable<ResponseBody>
}