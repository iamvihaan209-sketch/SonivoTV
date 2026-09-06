package com.vihaan.sonivo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface MusicRepository {
    fun getSongs(): Flow<List<Song>>
    fun getAlbums(): Flow<List<Album>>
    fun getPlaylists(): Flow<List<Playlist>>
}

class MockMusicRepository : MusicRepository {
    private val songs = listOf(
        Song("1", "Song One", "Artist A", "a1", 180000, "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3", "https://example.com/a1.jpg"),
        Song("2", "Song Two", "Artist B", "a2", 200000, "https://storage.googleapis.com/exoplayer-test-media-0/babylon.mp3", "https://example.com/a2.jpg"),
        Song("3", "Song Three", "Artist A", "a1", 210000, "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3", "https://example.com/a1.jpg")
    )

    private val albums = listOf(
        Album("a1", "Album One", "Artist A", "https://example.com/a1.jpg"),
        Album("a2", "Album Two", "Artist B", "https://example.com/a2.jpg")
    )

    private val playlists = listOf(
        Playlist("p1", "Chill Vibes", "Relaxing music for coding", "https://example.com/p1.jpg"),
        Playlist("p2", "Workout Hits", "Get moving with these tracks", "https://example.com/p2.jpg")
    )

    override fun getSongs(): Flow<List<Song>> = flowOf(songs)
    override fun getAlbums(): Flow<List<Album>> = flowOf(albums)
    override fun getPlaylists(): Flow<List<Playlist>> = flowOf(playlists)
}
