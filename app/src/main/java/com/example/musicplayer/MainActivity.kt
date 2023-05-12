package com.example.musicplayer


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.musicplayer.databinding.ActivityMainBinding
import com.example.musicplayer.fragments.HomeFragment
import com.example.musicplayer.fragments.LikeFragment
import com.example.musicplayer.fragments.PlaylistFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeFragment: HomeFragment
    private lateinit var playlistFragment: PlaylistFragment
    private lateinit var likeFragment: LikeFragment

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeFragment = HomeFragment()
        playlistFragment = PlaylistFragment()
        likeFragment = LikeFragment()
        binding.myBottomNav.defaultFocusHighlightEnabled = false
        binding.myBottomNav.selectedItemId = R.id.home


        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .add(binding.myContainer.id, homeFragment)
            .commit()

        binding.myBottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    fragmentManager.beginTransaction()
                        .addToBackStack(homeFragment.toString())
                        .replace(binding.myContainer.id, homeFragment)
                        .commit()
                }

                R.id.playlist -> {
                    fragmentManager.beginTransaction()
                        .addToBackStack(playlistFragment.toString())
                        .replace(binding.myContainer.id, playlistFragment)
                        .commit()
                }

                R.id.liked -> {
                    fragmentManager.beginTransaction()
                        .addToBackStack(likeFragment.toString())
                        .replace(binding.myContainer.id, likeFragment)
                        .commit()
                }
            }
            true
        }
    }

//    override fun onBackPressed() {
//        finish()
//    }
}