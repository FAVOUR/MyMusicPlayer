package com.example.mymusicplayer.data.source.remote.model

import com.example.mymusicplayer.data.source.local.entity.Song
import com.google.gson.annotations.SerializedName

data class LearnFieldSongs(
    @SerializedName("data")
    val songs: List<LearnFieldSongsItems>?
)

data class LearnFieldSongsItems(
    val audio: String,
    val cover: String,
    val title: String,
    val totalDurationMs: Int
)


fun LearnFieldSongsItems.toSong(): Song {
    return run{
        Song(
            title = title,
            coverUrl = cover,
            totalDurationMs =
            totalDurationMs.toLong(),
            audioUrl = audio,
            isFavourite = false,
            pathToAudio = "",
            rating = 0
        )
    }
}