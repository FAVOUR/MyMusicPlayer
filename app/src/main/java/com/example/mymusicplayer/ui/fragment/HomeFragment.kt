package com.example.mymusicplayer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusicplayer.R
import com.example.mymusicplayer.databinding.FragmentHomeBinding
import com.example.mymusicplayer.ui.HomeRecyclerViewUiState
import com.example.mymusicplayer.ui.HomeRecyclerViewUiState.Favourite
import com.example.mymusicplayer.ui.HomeRecyclerViewUiState.NavigateToNextPage
import com.example.mymusicplayer.ui.HomeUiState
import com.example.mymusicplayer.ui.HomeUiState.*
import com.example.mymusicplayer.ui.recyclerView.adapters.SongsRecyclerViewAdapter
import com.example.mymusicplayer.ui.util.*
import com.example.mymusicplayer.ui.util.Constants.SONG_DATA
import com.example.mymusicplayer.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by activityViewModels<HomeViewModel>()

    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    private lateinit var songsRecyclerViewAdapter: SongsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        songsRecyclerViewAdapter = SongsRecyclerViewAdapter(::recyclerAdapter)

        fragmentHomeBinding.songsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = songsRecyclerViewAdapter
        }
        homeViewModel.fetchAllLearningFieldAudio()
        viewLifecycleOwner.observe(homeViewModel.songsObs, ::observeSongState)
    }


    private fun recyclerAdapter(homeRecyclerViewUiState: HomeRecyclerViewUiState) {
        when (homeRecyclerViewUiState) {
            is Favourite -> {
                with(homeRecyclerViewUiState) {
                    homeViewModel.updateFavourite(isFavourite, songTitle)
                }
            }
            is NavigateToNextPage -> {
                val args = Bundle()
                args.apply {
                    putParcelable(SONG_DATA, homeRecyclerViewUiState.song)
                }
                requireActivity().navigateWithArgsTo(destId = R.id.action_homeFragment_to_music_player_Fragment,
                    args = args)
            }
        }
    }

    private fun observeSongState(songUiState: HomeUiState?) {
        requireActivity().dismissProgress()
        when (songUiState) {
            is Success -> {
                songsRecyclerViewAdapter.submitList(songUiState.data)
            }
            is Loading -> {
                requireActivity().showProgress(getString(R.string.fetching_songs),
                    getString(R.string.please_wait))
            }
            is Error -> {
                requireActivity().onErrorMessage(title = getString(R.string.error),
                    throwable = songUiState.exception)
            }
            else -> {

            }
        }
    }


}