package com.mirim.view.swing

import com.mirim.presentation.dto.FrameState
import com.mirim.view.GameView
import java.awt.Canvas
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferStrategy
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame

class SwingWindow(
    private val width: Int,
    private val height: Int,
    private val title: String
) {
    private val frame = JFrame(title)
    private val canvas = object : Canvas() {}

    val inputState = InputState()
    val view: GameView = object : GameView {
        override fun render(state: FrameState) {
            renderInternal(state)
        }
    }

    fun open() {
        canvas.preferredSize = Dimension(width, height)
        canvas.isFocusable = true

        canvas.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                inputState.onKey(e.keyCode, true)
            }

            override fun keyReleased(e: KeyEvent) {
                inputState.onKey(e.keyCode, false)
            }
        })

        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isResizable = false
        frame.add(canvas)
        frame.pack()
        frame.setLocationRelativeTo(null)
        frame.isVisible = true

        canvas.requestFocus()
        canvas.createBufferStrategy(3)
    }

    fun close() {
        frame.dispose()
    }

    private fun renderInternal(state: FrameState) {
        val bs: BufferStrategy = canvas.bufferStrategy ?: return

        do {
            do {
                val g: Graphics = bs.drawGraphics
                try {
                    g.clearRect(0, 0, width, height)
                    g.drawString(state.debugText, 20, 30)
                } finally {
                    g.dispose()
                }
            } while (bs.contentsRestored())
            bs.show()
        } while (bs.contentsLost())
    }

    class InputState {
        @Volatile var left = false
            private set
        @Volatile var right = false
            private set
        @Volatile var fire = false
            private set

        fun onKey(keyCode: Int, pressed: Boolean) {
            when (keyCode) {
                KeyEvent.VK_LEFT -> left = pressed
                KeyEvent.VK_RIGHT -> right = pressed
                KeyEvent.VK_SPACE -> fire = pressed
            }
        }
    }
}
