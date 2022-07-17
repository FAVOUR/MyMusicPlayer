package com.example.mymusicplayer.ui

import android.app.Activity
import com.example.mymusicplayer.data.source.local.entity.Song

sealed class SongsUiState {
    data class Success(val data: Song): SongsUiState()
    data class Error(val exception: Throwable): SongsUiState()
    object Loading : SongsUiState()
}