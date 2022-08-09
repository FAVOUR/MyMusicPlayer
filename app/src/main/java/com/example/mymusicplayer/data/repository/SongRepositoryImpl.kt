package com.example.mymusicplayer.data.repository

import com.example.mymusicplayer.data.repository.model.Result
import com.example.mymusicplayer.data.source.local.LocalSongDataSourceImpl
import com.example.mymusicplayer.data.source.local.entity.Song
import com.example.mymusicplayer.data.source.remote.RemoteSongsDataSource
import com.example.mymusicplayer.data.source.remote.model.toSong
import com.example.mymusicplayer.ui.util.createResult
import io.reactivex.Observable
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val remoteSongsDataSource: RemoteSongsDataSource,
    private val localSongDataSource: LocalSongDataSourceImpl,
) : SongRepository {

    override fun obtainSongs(): Observable<Result<List<Song>>> {
        return remoteSongsDataSource.getSongs()
            .flatMap { learnFieldSong ->
                if (learnFieldSong.songs != null) {
                    learnFieldSong.songs.map {
                        storeSongsCacheSongs(it.toSong())
                    }
                }
                Observable.just(learnFieldSong.songs!!.map { it.toSong() }
                )
            }.createResult({
                val hold: List<Song> = it
                Result.Success(data = hold)
            }, { Result.Error(IllegalArgumentException("")) })

    }

    override fun downloadSpecificSong(specificSong: Song): Observable<Result<Song>> {
        return remoteSongsDataSource.downloadAudio(specificSong.audioUrl) //change to single and use flatmap
            //throw Exception or set the error
            .flatMap {
                val stream = it.byteStream()
                val path =
                    localSongDataSource.saveAudioToDeviceAndStorePath(song = specificSong, stream)
                val song = specificSong.copy(pathToAudio = path)
                if (path.isNotEmpty()) {
                    storeSongsCacheSongs(song)
                }
                Observable.just(song) //optimize this
            }.createResult({ Result.Success(it) }) { Result.Error(it) }
    }

    override fun storeSongsCacheSongs(song: Song) =
        localSongDataSource.storeSongInDb(song)
            .createResult({ Result.Success(it) }) { Result.Error(it) }

    override fun observeSpecificSong(songTitle: String): Observable<Result<Song>> {
        return localSongDataSource.obtainSongByTitle(titleOfSong = songTitle)
            .toObservable()
            .createResult({ Result.Success(it) }) { Result.Error(it) }
    }

}