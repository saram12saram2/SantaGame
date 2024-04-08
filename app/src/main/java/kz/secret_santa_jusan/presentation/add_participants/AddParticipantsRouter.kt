package kz.secret_santa_jusan.presentation.add_participants

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class AddParticipantsRouter : ScreenProvider {
    class AddParticipantsScreen(
        val isAuth:Boolean
    ) : AddParticipantsRouter()
}