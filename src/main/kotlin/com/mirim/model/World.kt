package com.mirim.model

import com.mirim.view.swing.SwingWindow

class World {
    private var tickCount: Long = 0

    fun update(input: SwingWindow.InputState) {
        // TODO: 실제 게임 로직 (이동/충돌/스폰)
        tickCount++
    }

    fun debugString(): String = "World tick=$tickCount"
}
