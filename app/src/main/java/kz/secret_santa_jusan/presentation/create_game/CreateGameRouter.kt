package kz.secret_santa_jusan.presentation.create_game

import cafe.adriel.voyager.core.registry.ScreenProvider
import kz.secret_santa_jusan.presentation.add_participants.AddParticipantsRouter

sealed class CreateGameRouter : ScreenProvider {
    class CreateGameScreen(
        val isAuth:Boolean
    ) : CreateGameRouter()
}