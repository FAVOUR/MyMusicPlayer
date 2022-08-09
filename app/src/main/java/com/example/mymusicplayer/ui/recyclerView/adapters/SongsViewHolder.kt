package com.example.mymusicplayer.ui.recyclerView.adapters

import android.content.Context
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymusicplayer.R
import com.example.mymusicplayer.data.source.local.entity.Song
import com.example.mymusicplayer.databinding.AllSongsItemLayoutBinding
import com.example.mymusicplayer.ui.HomeRecyclerViewUiState

class SongsViewHolder(
    private val binding: AllSongsItemLayoutBinding,
    private val onClick: (HomeRecyclerViewUiState) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(position: Int, specificSong: Song) {
        with(specificSong) {
            //make this resusble
            binding.favouriteImgV.setImageDrawable(getFavouriteImageState(isFavourite).getDrawable(
                binding.root.context))
            binding.songTitleTv.text = title
//          binding.songCoverImgV.setImageDrawable() //use glide
            binding.ratingRatB.rating = rating.toFloat()
            binding.favouriteImgV.setOnClickListener {
                //optimize
                binding.favouriteImgV.setImageDrawable(getFavouriteImageState(!isFavourite).getDrawable(
                    binding.root.context))
                onClick(HomeRecyclerViewUiState.Favourite(isFavourite = !isFavourite,
                    songTitle = title))
            }
            binding.root.setOnClickListener {
                onClick(HomeRecyclerViewUiState.NavigateToNextPage(song = specificSong))
            }

            Glide.with(binding.root.context)
                .load(Uri.parse(specificSong.coverUrl))
                .into(binding.songCoverImgV)
        }
    }

    private fun Int.getDrawable(context: Context) =
        ContextCompat.getDrawable(context, this)

    private fun getFavouriteImageState(isFavourite: Boolean): Int {
        return if (isFavourite)
            R.drawable.heart_filled_black
        else
            R.drawable.heart_line_black
    }


}