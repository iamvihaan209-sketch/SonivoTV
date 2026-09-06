package com.vihaan.sonivo

import android.app.Application
import com.vihaan.sonivo.data.MockMusicRepository
import com.vihaan.sonivo.data.MusicRepository
import com.vihaan.sonivo.engage.EngageBroadcastReceiver
import com.vihaan.sonivo.engage.EngagePublisher
import com.vihaan.sonivo.playback.PlaybackConnection

class SonivoApp : Application() {
    lateinit var musicRepository: MusicRepository
    lateinit var playbackConnection: PlaybackConnection

    override fun onCreate() {
        super.onCreate()
        musicRepository = MockMusicRepository()
        playbackConnection = PlaybackConnection(this)

        // Initialize Engage SDK
        EngageBroadcastReceiver.register(this)
        EngagePublisher(this).schedulePeriodicPublishing()
    }
}
