package com.example.musicplayer.fragments

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicplayer.databinding.FragmentClickBinding
import com.example.musicplayer.models.MyMusic


class ClickFragment : Fragment() {

    private lateinit var binding: FragmentClickBinding

    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClickBinding.inflate(layoutInflater)
        val music = arguments?.getSerializable("keyMusic") as MyMusic
        mediaPlayer = MediaPlayer()
        val musicUri = Uri.parse(music.musicPath)
        mediaPlayer = MediaPlayer.create(binding.root.context, musicUri)

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnPlay.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }
        }

        return binding.root
    }


}