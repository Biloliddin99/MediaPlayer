package com.example.musicplayer.models

import java.io.Serializable


data class MyMusic (
    val id:Long,
    val title:String,
    val imagePath:String,
    val musicPath: String,
    val artist:String
):Serializable
