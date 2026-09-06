package com.vihaan.sonivo.data

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val albumId: String,
    val durationMs: Long,
    val mediaUrl: String,
    val artworkUrl: String
)

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val imageUrl: String
)

data class Playlist(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String
)
