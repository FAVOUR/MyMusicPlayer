package com.example.mymusicplayer.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Song(
    @PrimaryKey
    val title: String,
    val audioUrl: String,
    val coverUrl: String,
    val pathToAudio: String,
    val totalDurationMs: Int,
    val isFavourite: Boolean,
    val rating: Int,
)