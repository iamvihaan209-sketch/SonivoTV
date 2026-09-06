package com.vihaan.sonivo.navigation

import kotlinx.serialization.Serializable

sealed interface NavKey

@Serializable
data object Home : NavKey

@Serializable
data object Search : NavKey

@Serializable
data object Library : NavKey

@Serializable
data object NowPlaying : NavKey
