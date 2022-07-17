package com.example.mymusicplayer.ui.fragment

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.mymusicplayer.R
import com.example.mymusicplayer.data.source.local.entity.Song
import com.example.mymusicplayer.databinding.FragmentMusicPlayerBinding
import com.example.mymusicplayer.ui.MusicPlayerUiState
import com.example.mymusicplayer.ui.MusicPlayerUiState.*
import com.example.mymusicplayer.ui.util.Constants.SONG_DATA
import com.example.mymusicplayer.ui.util.dismissProgress
import com.example.mymusicplayer.ui.util.observe
import com.example.mymusicplayer.ui.util.onErrorMessage
import com.example.mymusicplayer.ui.util.showProgress
import com.example.mymusicplayer.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MusicPlayerFragment : Fragment() {

    private lateinit var fragmentMusicPlayerBinding: FragmentMusicPlayerBinding
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private var song: Song? = null
    var mediaPlayer: MediaPlayer =MediaPlayer()

//    private val songDetailViewModel by viewModels<SongDetailViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        song = arguments?.getParcelable(SONG_DATA) as? Song //pass Song title instead
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragmentMusicPlayerBinding = FragmentMusicPlayerBinding.inflate(inflater,container,false)
        return fragmentMusicPlayerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.observe(homeViewModel.musicPlayerObs,::observeSongState)
        song?.let {
            setupView(it)
        }

        setupListeners()

        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    fragmentMusicPlayerBinding.seekBar.setProgress(mediaPlayer.getCurrentPosition())
                    fragmentMusicPlayerBinding.currentTimeTv.text =  mediaPlayer.getCurrentPosition().toLong().convertToMMSS()
                }
                Handler().postDelayed(this, 100)
            }
        })

        fragmentMusicPlayerBinding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


    }

    private fun setupView (it: Song) {
        fragmentMusicPlayerBinding.apply {
            songTitleTv.text = it.title
            Glide.with(fragmentMusicPlayerBinding.root.context)
                .load(Uri.parse(it.coverUrl))
                .into(fragmentMusicPlayerBinding.pausePlayImgBtn)

            totalTimeTv.text =it.totalDurationMs.convertToMMSS()
            fragmentMusicPlayerBinding.pausePlayImgBtn.setOnClickListener(View.OnClickListener { v: View? -> pausePlay(it.pathToAudio) })
        }
    }

    private fun setupListeners() {
        fragmentMusicPlayerBinding.pausePlayImgBtn.setOnClickListener {
            song?.let {
                if(it.pathToAudio.isNullOrEmpty())
                homeViewModel.fetchAudio(song)
                else
                pausePlay(it.pathToAudio)
            }
        }
    }

    private fun observeSongState(songUiState: MusicPlayerUiState?){
        requireActivity().dismissProgress()
        when(songUiState) {
            is Success -> {
                playMusic(songUiState.data.pathToAudio)
            }
            is Loading ->{
              requireActivity().showProgress(getString(R.string.fetching_songs),getString(R.string.please_wait))
            }
            is Error ->{
              requireActivity().onErrorMessage(title = getString(R.string.error), throwable = songUiState.exception)
            }
            else ->{

            }
        }
    }


    private fun playMusic(path:String) {
        mediaPlayer.reset()
        try {
//            mediaPlayer.setDataSource(currentSong.getPath())
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepare()
            mediaPlayer.start()
            fragmentMusicPlayerBinding.seekBar.setProgress(0)
            fragmentMusicPlayerBinding.seekBar.setMax(mediaPlayer.getDuration())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun pausePlay(path:String) {
        if (mediaPlayer.isPlaying) mediaPlayer.pause() else playOrContinue(path)
    }

    private fun playOrContinue(path:String){
        if(mediaPlayer.duration > 0){
            mediaPlayer.start()
        }else{
            playMusic(path)

        }
    }


    fun Long.convertToMMSS(): String? {
        return String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(this) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(this) % TimeUnit.MINUTES.toSeconds(1))
    }
}
