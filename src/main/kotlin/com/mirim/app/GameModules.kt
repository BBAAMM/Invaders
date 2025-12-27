package com.mirim.app

import com.mirim.controller.GameController
import com.mirim.controller.loop.FixedStepLoop
import com.mirim.infra.assets.AssetManager
import com.mirim.infra.config.*
import com.mirim.model.World
import com.mirim.presentation.GamePresenter
import com.mirim.view.swing.SwingWindow

class GameModules {

    fun build(): GameContext{
        val config: AppConfig = provideConfig()
        val assets: AssetManager = provideAssets(config)
        val window: SwingWindow = provideWindow(config)
        val presenter: GamePresenter = providePresenter(config)
        val controller: GameController = provideController(config, assets, window, presenter)
        val loop: FixedStepLoop = provideLoop(config, controller)

        return GameContext(
            loop = object : LoopHandle{
                override fun start() = loop.start()
                override fun stop() = loop.stop()
            },
            window = object : WindowHandle{
                override fun close() = window.close()
            },
            assets = object : CloseableHandle{
                override fun close() = assets.close()
            },
        )
    }

// ----- providers -----

    private fun provideConfig(): AppConfig {
        val loader = JsonConfigLoader()
        return loader.loadResource<AppConfig>("config/appConfig.json").also{ it.validate() }
    }

    private fun provideAssets(config: AppConfig): AssetManager {
        val assets = AssetManager()
        assets.preload(config) // 예: sprites/sounds 선로딩(선택)
        return assets
    }

    private fun provideWindow(config: AppConfig): SwingWindow {
        // SwingWindow 내부에서 Canvas를 만들고 JFrame에 attach한다고 가정
        return SwingWindow(title = config.game.title, width = config.game.screenWidth, height = config.game.screenHeight)
            .also { it.open() }
    }

    private fun providePresenter(config: AppConfig): GamePresenter {
        return GamePresenter(config)
    }

    private fun provideController(
        config: AppConfig,
        assets: AssetManager,
        window: SwingWindow,
        presenter: GamePresenter
    ): GameController {
        // controller는 window의 canvas/input을 받아 model update + view render orchestration
        val world = World()                 // TODO: 필요 시 config/content로 초기화
        val input = window.inputState       // SwingWindow가 관리하는 입력 상태
        val view = window.view              // SwingWindow가 제공하는 GameView

        return GameController(
            world = world,
            input = input,
            view = view,
            presenter = presenter
        )
    }

    private fun provideLoop(config: AppConfig, controller: GameController): FixedStepLoop {
        return FixedStepLoop(
            ups = config.game.ups,
            onTick = controller::tick
        )
    }
}