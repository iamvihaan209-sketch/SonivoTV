package com.vihaan.sonivo.engage

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.ListenableWorker
import com.google.android.engage.service.AppEngagePublishClient
import com.vihaan.sonivo.SonivoApp
import kotlinx.coroutines.flow.first

class EngageWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): ListenableWorker.Result {
        val app = applicationContext as SonivoApp
        val repository = app.musicRepository
        val client = AppEngagePublishClient(applicationContext)

        val publishType = inputData.getInt(
            EngageConstants.PUBLISH_TYPE_KEY,
            EngageConstants.PUBLISH_TYPE_ALL
        )

        return try {
            if (publishType == EngageConstants.PUBLISH_TYPE_ALL ||
                publishType == EngageConstants.PUBLISH_TYPE_RECOMMENDATION
            ) {
                val songs = repository.getSongs().first()
                val albums = repository.getAlbums().first()
                
                val tracks = songs.map { ItemToEntityConverter.convertSongToTrackEntity(it) }
                val albumEntities = albums.map { ItemToEntityConverter.convertAlbumToAlbumEntity(it) }
                
                val request = ClusterRequestFactory.createRecommendationRequest(
                    "Recommended for You",
                    tracks,
                    albumEntities
                )
                client.publishRecommendationClusters(request)
            }

            if (publishType == EngageConstants.PUBLISH_TYPE_ALL ||
                publishType == EngageConstants.PUBLISH_TYPE_CONTINUATION
            ) {
                val songs = repository.getSongs().first()
                val tracks = songs.map { ItemToEntityConverter.convertSongToTrackEntity(it) }
                
                val request = ClusterRequestFactory.createContinuationRequest(tracks)
                client.publishContinuationCluster(request)
            }

            ListenableWorker.Result.success()
        } catch (e: Exception) {
            ListenableWorker.Result.retry()
        }
    }
}
