package com.example.mymusicplayer.data.repository

import com.example.mymusicplayer.data.repository.model.Result
import com.example.mymusicplayer.data.source.local.LocalSongDataSourceImpl
import com.example.mymusicplayer.data.source.local.entity.Song
import com.example.mymusicplayer.data.source.remote.RemoteSongsDataSource
import com.example.mymusicplayer.data.source.remote.model.LearnFieldSongs
import com.example.mymusicplayer.data.source.remote.model.toSong
import io.reactivex.Maybe
import io.reactivex.Observable
import java.lang.IllegalArgumentException
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val remoteSongsDataSource: RemoteSongsDataSource,
    private val localSongDataSource: LocalSongDataSourceImpl,
) : SongRepository {

    override fun obtainSongs() { //: Observable<Result<Song>>
         remoteSongsDataSource.getSongs() //change to single and use flatmap
            .doOnNext{ learnFieldSong ->
                if (learnFieldSong.songs != null) {
                    learnFieldSong.songs.map {
                        storeSongsCacheSongs(it.toSong())
                    }
                }
            }.createResult({
                val hold:List<Song> = it.songs!!.map {it.toSong()}
                Result.Success(data = hold)
            } ,{ Result.Error(java.lang.Exception("")) } )
//            .createResult({ Result.Success(it.songs.) }){ Result.Error(it) }

    }

    override fun downloadSpecificSong(songUrl: String, specificSong: Song): Observable<Result<String>> {
        return remoteSongsDataSource.downloadAudio(songUrl) //change to single and use flatmap
            //throw Exception or set the error
            .flatMap{
                val stream = it.byteStream()
               val path =  localSongDataSource.saveAudioToDeviceAndStorePath(song = specificSong, stream)
                if (path.isNotEmpty()){
                    storeSongsCacheSongs(specificSong.copy(pathToAudio = path))
                }
                Observable.just(path) //optimize this
            }.createResult({ Result.Success(it) }){ Result.Error(it) }
    }

    override fun storeSongsCacheSongs(song: Song) =
        localSongDataSource.storeSongInDb(song)
        .createResult({ Result.Success(it) }){ Result.Error(it) }

    override fun observeSpecificSong(songTitle: String): Observable<Result<Song>> {
        return localSongDataSource.obtainSongByTitle(titleOfSong = songTitle)
            .toObservable()
            .createResult({ Result.Success(it) }){ Result.Error(it) }
    }

    //move to helper class

    fun <T : Any> Observable<T>.createResult(
        success: (T) -> Result<T>,
        failure: (Exception) -> Result<T>,
    ): Observable<Result<T>> {
        return try {
            map {
                if (it != null) {
                    success(it)
                } else {
                    failure(IllegalArgumentException("Response Is empty"))//Do not hardcode
                }
            }
        } catch (e: Throwable) {
            Observable.just(Result.Error(java.lang.Exception("An Error Occurred")))//Do not hardcode
        }
    }

    fun <T : Any> Maybe<T>.createResult(
        success: (T) -> Result<T>,
        failure: (Exception) -> Result<T>,
    ): Maybe<Result<T>> {
        return try {
            map {
                if (it != null) {
                    success(it)
                } else {
                    failure(IllegalArgumentException("Response Is empty")) //Do not hardcode
                }
            }
        } catch (e: Throwable) {
            Maybe.just(Result.Error(java.lang.Exception("An Error Occurred")))//Do not hardcode
        }
    }

}