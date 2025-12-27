package com.mirim.app

class GameApplication(
    private val modules:GameModules = GameModules()
) {
    private var ctx: GameContext? = null

    fun start() {
        check(ctx == null) {"GameApplication is already started."}

        val built = modules.build()
        ctx = built

        built.loop.start()
    }

    fun stop() {
        ctx?.let {
            it.loop.stop()
            it.window.close()
            it.assets.close()
        }
        ctx = null
    }
}

data class GameContext(
    val loop: LoopHandle,
    val window: WindowHandle,
    val assets: CloseableHandle
)

interface LoopHandle {
    fun start()
    fun stop()
}

interface WindowHandle {
    fun close()
}

interface CloseableHandle {
    fun close()
}