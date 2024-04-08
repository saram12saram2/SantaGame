package kz.secret_santa_jusan.presentation.create_game

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.data.game.GameApiRepository
import kz.secret_santa_jusan.data.game.models.CreateGameModel
import trikita.log.Log
import java.util.UUID

class CreateGameViewModel(
    private val repository: GameApiRepository
) : CoreBaseViewModel(), ICreateGameViewModel {
    private val _state = MutableStateFlow(CreateGameState())
    override val state: StateFlow<CreateGameState> = _state.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    override fun sendEvent(event: CreateGameEvent) {
        when (event) {
            is CreateGameEvent.Init -> {
            }

            is CreateGameEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            is CreateGameEvent.CreateGame -> {
                createGame()
            }

            is CreateGameEvent.EnterGameName -> {
                _state.update { it.copy(gameName = event.text) }
            }

            is CreateGameEvent.EnterGameId -> {
                _state.update { it.copy(gameId = event.text) }
            }
            else -> {}
        }
    }


    private fun createGame() = with(state.value) {
        screenModelScope.launch {
            repository.createGame(
                CreateGameModel(
                    name = gameName,
                    maxPrice = 1,
                    uniqueIdentifier = gameId,
                    priceLimitChecked = false
                )
            ).apply {
                if (isSuccessful) {
                    GlobalStorage.saveCurrentGameId(body.id!!)
                    Log.d("ok", "ok")
                    _navigationEvent.value = NavigationEvent.GoToAddParticipants
                }
            }
        }
    }
}

interface ICreateGameViewModel {
    val state: StateFlow<CreateGameState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: CreateGameEvent)
}

class CreateGameViewModelPreview : ICreateGameViewModel {
    override val state: StateFlow<CreateGameState> = MutableStateFlow(
        CreateGameState()
    ).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: CreateGameEvent) {}
}


sealed class CreateGameEvent {
    object Init : CreateGameEvent()
    object Back : CreateGameEvent()
    object CreateGame : CreateGameEvent()

    class EnterGameName(val text: String) : CreateGameEvent()
    class EnterGameId(val text: String) : CreateGameEvent()
}

data class CreateGameState(val gameName: String = "", val gameId: String = "")

sealed class NavigationEvent {
    private var handled: Boolean = false

    fun getValue(): NavigationEvent {
        if (handled) return Default()
        handled = true
        return this
    }

    class Default : NavigationEvent()
    class Back : NavigationEvent()
    object GoToAddParticipants : NavigationEvent()
}
