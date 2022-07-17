package com.example.mymusicplayer.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mymusicplayer.R

class SongDetailFragment : Fragment() {

    companion object {
        fun newInstance() = SongDetailFragment()
    }

    private lateinit var viewModel: SongDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_song_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SongDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}