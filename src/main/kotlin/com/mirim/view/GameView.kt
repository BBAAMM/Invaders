package com.mirim.view

import com.mirim.presentation.dto.FrameState

interface GameView {
    fun render(state: FrameState)
}