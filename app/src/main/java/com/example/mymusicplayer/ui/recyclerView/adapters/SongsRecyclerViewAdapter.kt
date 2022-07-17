package com.example.mymusicplayer.ui.recyclerView.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mymusicplayer.data.source.local.entity.Song
import com.example.mymusicplayer.databinding.AllSongsItemLayoutBinding
import com.example.mymusicplayer.ui.HomeRecyclerViewUiState

class SongsRecyclerViewAdapter(private val onClick: (HomeRecyclerViewUiState) ->Unit): ListAdapter<Song,SongsViewHolder>(SongDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
         val binding = AllSongsItemLayoutBinding.inflate(LayoutInflater.from(parent.context))

        return SongsViewHolder(binding,onClick)

    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.bind(position,getItem(position))
    }


    class SongDiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }



}