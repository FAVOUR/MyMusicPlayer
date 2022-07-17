package com.example.mymusicplayer.data.source.remote

import com.example.mymusicplayer.data.source.remote.model.LearnFieldSongs
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import java.io.InputStream

interface RemoteSongsDataSource {
    fun getSongs(): Observable<LearnFieldSongs>
    fun downloadAudio(url:String): Observable<ResponseBody>
}