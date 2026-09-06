package com.vihaan.sonivo.engage

import com.google.android.engage.common.datamodel.ContinuationCluster
import com.google.android.engage.audio.datamodel.MusicAlbumEntity
import com.google.android.engage.audio.datamodel.MusicTrackEntity
import com.google.android.engage.common.datamodel.RecommendationCluster
import com.google.android.engage.service.PublishContinuationClusterRequest
import com.google.android.engage.service.PublishRecommendationClustersRequest

object ClusterRequestFactory {
    fun createRecommendationRequest(
        title: String,
        tracks: List<MusicTrackEntity>,
        albums: List<MusicAlbumEntity>
    ): PublishRecommendationClustersRequest {
        val recommendationClusterBuilder = RecommendationCluster.Builder()
            .setTitle(title)
        
        tracks.forEach { recommendationClusterBuilder.addEntity(it) }
        albums.forEach { recommendationClusterBuilder.addEntity(it) }

        return PublishRecommendationClustersRequest.Builder()
            .addRecommendationCluster(recommendationClusterBuilder.build())
            .build()
    }

    fun createContinuationRequest(
        tracks: List<MusicTrackEntity>
    ): PublishContinuationClusterRequest {
        val continuationClusterBuilder = ContinuationCluster.Builder()
        
        tracks.forEach { continuationClusterBuilder.addEntity(it) }

        return PublishContinuationClusterRequest.Builder()
            .setContinuationCluster(continuationClusterBuilder.build())
            .build()
    }
}
