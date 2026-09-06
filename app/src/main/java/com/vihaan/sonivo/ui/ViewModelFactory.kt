package com.vihaan.sonivo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vihaan.sonivo.data.MusicRepository
import com.vihaan.sonivo.playback.PlaybackConnection
import com.vihaan.sonivo.ui.home.HomeViewModel
import com.vihaan.sonivo.ui.library.LibraryViewModel
import com.vihaan.sonivo.ui.nowplaying.NowPlayingViewModel
import com.vihaan.sonivo.ui.search.SearchViewModel

class ViewModelFactory(
    private val repository: MusicRepository,
    private val playbackConnection: PlaybackConnection
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository) as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(repository) as T
            modelClass.isAssignableFrom(LibraryViewModel::class.java) -> LibraryViewModel(repository) as T
            modelClass.isAssignableFrom(NowPlayingViewModel::class.java) -> NowPlayingViewModel(repository, playbackConnection) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
