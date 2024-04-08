package kz.secret_santa_jusan.presentation.invitation

import cafe.adriel.voyager.core.registry.ScreenProvider
import kz.secret_santa_jusan.presentation.main.MainRouter

sealed class InvitationRouter : ScreenProvider {
    class InvitationSreen(
        val isAuth:Boolean
    ) : InvitationRouter()
}