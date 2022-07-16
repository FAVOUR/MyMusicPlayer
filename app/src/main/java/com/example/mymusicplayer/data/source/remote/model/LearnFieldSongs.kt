package com.example.mymusicplayer.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class LearnFieldSongs(
    @SerializedName("data")
    val songs: List<Data>?
)

data class Data(
    val audio: String,
    val cover: String,
    val title: String,
    val totalDurationMs: Int
)