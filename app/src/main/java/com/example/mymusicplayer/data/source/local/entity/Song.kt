package com.example.mymusicplayer.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Song(
    @PrimaryKey
    val title: String,
    val audioUrl: String,
    val coverUrl: String,
    val pathToAudio: String,
    val totalDurationMs: Long,
    val isFavourite: Boolean,
    val rating: Int,
):Parcelable