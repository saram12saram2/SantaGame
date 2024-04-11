package kz.secret_santa_jusan.presentation.auth


import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.data.auth.AuthApiRepository
import kz.secret_santa_jusan.data.auth.models.AuthModel
import kz.secret_santa_jusan.data.registration.RegisterApiRepository
import kz.secret_santa_jusan.data.registration.models.RegModel
import kz.secret_santa_jusan.presentation.registration.RegistrationEvent
import kz.secret_santa_jusan.presentation.registration.RegistrationState
import trikita.log.Log

interface IAuthViewModel {
    val state: StateFlow<AuthState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: AuthEvent)
}

sealed class AuthEvent{
    object Back: AuthEvent()

    class EnterLogin(val text: String): AuthEvent()
    class EnterPassword(val text: String): AuthEvent()

    object GoToRecovery:AuthEvent()

    object ClickEnter: AuthEvent()

}

sealed class NavigationEvent{
    private var handled: Boolean = false

    fun getValue(): NavigationEvent {
        if (handled) return Default()
        handled = true
        return this
    }
    class Default: NavigationEvent()
    class Back: NavigationEvent()
    object GoToRecovery:NavigationEvent()

    object GoToMain:NavigationEvent()
}

data class AuthState(val authForm: AuthModel)

class AuthViewModelPreview : IAuthViewModel {
    override val state: StateFlow<AuthState> = MutableStateFlow(AuthState(AuthModel("",""))).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: AuthEvent) {}
}

class AuthViewModel(
    private val repository: AuthApiRepository
): CoreBaseViewModel(), IAuthViewModel {

    private var _state = MutableStateFlow<AuthState>(AuthState(AuthModel("","")))
    override val state: StateFlow<AuthState> = _state.asStateFlow()


    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        screenModelScope.launch {
        }
    }

    override fun sendEvent(event: AuthEvent) {
        when(event){
            AuthEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            AuthEvent.ClickEnter -> {
                screenModelScope.launch {
                    repository.auth(state.value.authForm).apply {
                        if(isSuccessful) {
                            Log.d("ok", "ok")
                            GlobalStorage.saveAuthToken(body.accessToken, body.refreshToken)
                            _navigationEvent.value = NavigationEvent.GoToMain
                        }
                    }
                }
            }
            is AuthEvent.EnterLogin -> {
                _state.update {
                    it.copy(authForm = it.authForm.copy(email = event.text))
                }
            }
            is AuthEvent.EnterPassword -> {
                _state.update {
                    it.copy(authForm = it.authForm.copy(password = event.text))
                }
            }

            AuthEvent.GoToRecovery -> {
                _navigationEvent.value = NavigationEvent.GoToRecovery
            }
        }
    }
}
