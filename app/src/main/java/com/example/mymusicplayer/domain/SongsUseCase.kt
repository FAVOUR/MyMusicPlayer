package com.example.mymusicplayer.domain

import com.example.mymusicplayer.data.repository.SongRepository
import com.example.mymusicplayer.data.source.remote.model.LearnFieldSongs
import io.reactivex.Observable
import javax.inject.Inject

//class SongsUseCase @Inject constructor(private val songsRepository: SongRepository) {
//
//    fun getSongs(): Observable<Result<LearnFieldSongs>> {
//        return try {
//            songsRepository.obtainSongs()
//                .map {
//                    if(it != null){
//                        Result.success(it)
//                    }else{
//                        Result.failure(Exception("") )
//                    }
//                }
//        }catch (e:Exception){
//           Observable.just(Result.failure(Exception("") ))
//        }
//    }
//}
//
//fun downloadSongs(): Observable<Result<LearnFieldSongs>> {
//    return try {
//        songsRepository.obtainSongs()
//            .map {
//                if(it != null){
//                    Result.success(it)
//                }else{
//                    Result.failure(Exception("") )
//                }
//            }
//    }catch (e:Exception){
//        Observable.just(Result.failure(Exception("") ))
//    }
//    }
//
//fun <T> Observable<T>.createResult(): Observable<Result<T>> {
//    return map {
//        if(it != null){
//    }
//}
//
//}