package com.mirim.infra.config

import kotlinx.serialization.Serializable

@Serializable
data class AppConfig(
    val game: GameConfig = GameConfig(),
    val audio: AudioConfig = AudioConfig()
)

@Serializable
data class LevelsConfig(
    val levels: List<LevelConfig> = emptyList()
)

@Serializable
data class GameConfig(
    val title: String = "Invaders",
    val screenWidth: Int = 1228,
    val screenHeight: Int = 777,
    val ups: Int = 60,
    val vsync: Boolean = false
)

@Serializable
data class AudioConfig(
    val master: Float = 1.0f,
    val sfx: Float = 1.0f,
    val bgm: Float = 1.0f
)

@Serializable
data class LevelConfig(
    val level: Int,
    val enemySpeed: Float
)