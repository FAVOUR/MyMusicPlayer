package com.example.mymusicplayer.ui

import com.example.mymusicplayer.data.source.local.entity.Song

sealed class HomeUiState {
    data class Success(val data: List<Song>): HomeUiState()
    data class Error(val exception: Throwable): HomeUiState()
    object Loading : HomeUiState()
}

sealed class MusicPlayerUiState {
    data class Success(val data: Song): MusicPlayerUiState()
    data class Error(val exception: Throwable): MusicPlayerUiState()
    object Loading : MusicPlayerUiState()
}

sealed class HomeRecyclerViewUiState {
    data class Favourite(val isFavourite: Boolean,val songTitle:String): HomeRecyclerViewUiState()
    data class NavigateToNextPage(val song: Song): HomeRecyclerViewUiState()
}