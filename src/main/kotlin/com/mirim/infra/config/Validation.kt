package com.mirim.infra.config

fun AppConfig.validate() {
    require(game.screenWidth > 0 && game.screenHeight > 0) { "Invalid resolution." }
    require(game.ups in 1..1000) { "Invalid ups: ${game.ups}" }
    require(audio.master in 0f..1f) { "Invalid master volume: ${audio.master}" }
}