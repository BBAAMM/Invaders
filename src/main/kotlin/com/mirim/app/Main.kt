package com.mirim.app

import com.mirim.infra.logging.Logging
import java.util.logging.Level
import java.util.logging.Logger
import javax.swing.SwingUtilities

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Logging.init(appName = "invaders", level = Level.INFO)
        Logger.getLogger(this::class.simpleName).info("Starting invaders...")

        SwingUtilities.invokeLater {
            GameApplication().start()
        }
    }
}