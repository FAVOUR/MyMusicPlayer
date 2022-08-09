package com.example.mymusicplayer.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymusicplayer.data.repository.SongRepository
import com.example.mymusicplayer.data.repository.model.Result
import com.example.mymusicplayer.data.source.local.entity.Song
import com.example.mymusicplayer.ui.HomeUiState
import com.example.mymusicplayer.ui.MusicPlayerUiState
import com.example.mymusicplayer.ui.util.standard
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val songRepository: SongRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val mSongsObs = MutableLiveData<HomeUiState>()
    val songsObs: MutableLiveData<HomeUiState>
        get() = mSongsObs

    private val mMusicPlayerObs = MutableLiveData<MusicPlayerUiState>()
    val musicPlayerObs: MutableLiveData<MusicPlayerUiState>
        get() = mMusicPlayerObs


    fun fetchAllLearningFieldAudio() {
        songsObs.postValue(HomeUiState.Loading)
        compositeDisposable.add(
            songRepository.obtainSongs()
                .standard()
                .subscribe {
                    when (it) {
                        is Result.Success -> {
                            songsObs.postValue(HomeUiState.Success(it.data))
                        }
                        else -> {

                        }
                    }
                }
        )
    }

    fun fetchAudio(song: Song?) {
        mMusicPlayerObs.postValue(MusicPlayerUiState.Loading)
        song?.let { nonemptySong ->
            compositeDisposable.add(
                songRepository.downloadSpecificSong(specificSong = nonemptySong)
                    .standard()
                    .subscribe {
                        when (it) {
                            is Result.Success -> {
                                mMusicPlayerObs.postValue(MusicPlayerUiState.Success(it.data))
                            }
                            else -> {

                            }
                        }
                    }
            )
        } ?: songsObs.postValue(HomeUiState.Error(IllegalArgumentException("No Song Was Passed")))
    }


    fun updateFavourite(favourite: Boolean, songTitle: String) {


    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}