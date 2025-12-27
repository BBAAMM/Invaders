package com.mirim.presentation

import com.mirim.infra.config.AppConfig
import com.mirim.model.World
import com.mirim.presentation.dto.FrameState

class GamePresenter(
    private val config: AppConfig
) {
    fun present(world: World): FrameState {
        // TODO: Model -> View DTO 변환(좌표변환/스프라이트 선택/HUD 계산)
        return FrameState(
            debugText = "${config.game.title} | ${world.debugString()}"
        )
    }
}