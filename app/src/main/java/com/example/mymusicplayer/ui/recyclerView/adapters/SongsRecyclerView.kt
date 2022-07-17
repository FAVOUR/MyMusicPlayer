package com.example.mymusicplayer.ui.recyclerView.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusicplayer.R
import com.example.mymusicplayer.data.source.local.entity.Song

class SongsRecyclerView(): ListAdapter<Song, SongsRecyclerView.SongsViewHolder>(songDiffCallback()) {


    inner class SongsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    class songDiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

       val view =  layoutInflater.inflate(R.layout.fragment_home,parent)

        SongsViewHolder(view)

    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {

    }

}