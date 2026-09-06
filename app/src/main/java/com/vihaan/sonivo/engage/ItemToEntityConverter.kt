package com.vihaan.sonivo.engage

import android.net.Uri
import com.google.android.engage.common.datamodel.Image
import com.google.android.engage.audio.datamodel.MusicAlbumEntity
import com.google.android.engage.audio.datamodel.MusicTrackEntity
import com.vihaan.sonivo.data.Album
import com.vihaan.sonivo.data.Song

object ItemToEntityConverter {
    fun convertSongToTrackEntity(song: Song): MusicTrackEntity {
        return MusicTrackEntity.Builder()
            .setName(song.title)
            .addArtist(song.artist)
            .addPosterImage(
                Image.Builder()
                    .setImageUri(Uri.parse(song.artworkUrl))
                    .build()
            )
            .setPlayBackUri(Uri.parse(song.mediaUrl))
            .setDurationMillis(song.durationMs)
            .build()
    }

    fun convertAlbumToAlbumEntity(album: Album): MusicAlbumEntity {
        return MusicAlbumEntity.Builder()
            .setName(album.title)
            .addArtist(album.artist)
            .addPosterImage(
                Image.Builder()
                    .setImageUri(Uri.parse(album.imageUrl))
                    .build()
            )
            .setInfoPageUri(Uri.parse("sonivo://album/${album.id}"))
            .build()
    }
}
