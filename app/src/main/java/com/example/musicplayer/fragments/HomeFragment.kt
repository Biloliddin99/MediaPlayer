package com.example.musicplayer.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.musicplayer.R
import com.example.musicplayer.adapters.MyRvAdapter
import com.example.musicplayer.adapters.RvClickInterface
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.models.MyData
import com.example.musicplayer.models.MyMusic
import com.github.florent37.runtimepermission.kotlin.askPermission


class HomeFragment : Fragment(), RvClickInterface{

    private lateinit var myRvAdapter: MyRvAdapter
    private lateinit var musicList: ArrayList<MyMusic>
    private lateinit var clickFragment: ClickFragment
    private lateinit var mediaPlayer: MediaPlayer

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        clickFragment = ClickFragment()


        return binding.root

    }

    private fun requestRuntimePermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                13
            )
        } else {
            readMusic()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestRuntimePermission()
    }

    @SuppressLint("Range")
    fun readMusic() {
        musicList = ArrayList()
        val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

        askPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
            // Request read external storage permission

            //all permissions already granted or just granted
            val cursor = requireContext().contentResolver.query(
                collection,
                null,
                null,
                null,
                sortOrder
            )
            if (cursor != null && cursor.moveToFirst()) {
                val id: Int = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
                val title: Int = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val authorId: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)
                val imageId: Int = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)

                val albumArtUri = Uri.parse("content://media/external/audio/albumart")
                val albumArtUriWithId =
                    ContentUris.withAppendedId(albumArtUri, cursor.getLong(imageId))

                // Now loop through the music files
                do {

                    val imagePath = albumArtUriWithId.toString()
                    val audioId: Long = cursor.getLong(id)
                    val audioTitle: String = cursor.getString(title)
//                        if (imageId != -1) {
//                            imagePath = cursor.getString(imageId)
//                        }
                    val musicPath: String =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val artist = cursor.getString(authorId)
                    val music = MyMusic(audioId, audioTitle, imagePath, musicPath, artist)
                    MyData.music.add(music)
                    musicList.add(music)
                } while (cursor.moveToNext())
            }
            cursor?.close()
            myRvAdapter = MyRvAdapter(requireContext(), musicList, this)
            binding.myRv.adapter = myRvAdapter
        }


    }

    override fun moreClick(myMusic: MyMusic, position: Int) {

    }

    override fun itemClick(myMusic: MyMusic, position: Int) {
        val bundle = Bundle()
//        mediaPlayer = MediaPlayer()
//        val musicUri:Uri = Uri.parse(myMusic.musicPath)
        println("<-----------------${myMusic.musicPath}--------------->")
//        mediaPlayer = MediaPlayer.create(requireContext(),musicUri)
//        mediaPlayer.prepare()
//        mediaPlayer.start()

        bundle.putSerializable("keyMusic",myMusic)
        clickFragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .addToBackStack(clickFragment.toString())
            .replace(R.id.my_container,clickFragment)
            .commit()
    }


}







