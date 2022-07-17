package com.example.mymusicplayer.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymusicplayer.data.repository.SongRepository
import com.example.mymusicplayer.data.repository.model.Result
import com.example.mymusicplayer.ui.util.standard
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val songRepository: SongRepository) : ViewModel() {

    private val mSongsObs = MutableLiveData<SongsUiState>()

     val songsObs: MutableLiveData<SongsUiState>
      get() = mSongsObs



    fun playMusic(){
        songsObs.postValue(SongsUiState.Loading)
        songRepository.obtainSongs()
            .standard()
            .subscribe{
                when (it){
                    is Result.Success ->{
                        songsObs.postValue(SongsUiState.Success(it.data))
                    }else -> {

                    }
                }
            }

    }
}