package com.mirim.controller

import com.mirim.model.World
import com.mirim.presentation.GamePresenter
import com.mirim.view.GameView
import com.mirim.view.swing.SwingWindow

class GameController(
    private val world: World,
    private val input: SwingWindow.InputState,
    private val view: GameView,
    private val presenter: GamePresenter
) {
    fun tick() {
        world.update(input)
        val frameState = presenter.present(world)
        view.render(frameState)
    }
}
