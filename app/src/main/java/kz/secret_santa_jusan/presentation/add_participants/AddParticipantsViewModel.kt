package kz.secret_santa_jusan.presentation.add_participants

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.data.game.GameApiRepository

class AddParticipantsViewModel(
    private val repository: GameApiRepository
) : CoreBaseViewModel(), IAddParticipantsViewModel {
    private val _state = MutableStateFlow(AddParticipantsState())
    override val state: StateFlow<AddParticipantsState> = _state.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    override fun sendEvent(event: AddParticipantsEvent) {
        when (event) {
            is AddParticipantsEvent.Init -> {
            }

            is AddParticipantsEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            AddParticipantsEvent.CreateOwnCard -> {
                _navigationEvent.value = NavigationEvent.GoToCreateOwnCard
            }

            else -> {}
        }
    }
}

interface IAddParticipantsViewModel {
    val state: StateFlow<AddParticipantsState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: AddParticipantsEvent)
}

class AddParticipantsEventViewModelPreview : IAddParticipantsViewModel {
    override val state: StateFlow<AddParticipantsState> = MutableStateFlow(
        AddParticipantsState()
    ).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: AddParticipantsEvent) {}
}


sealed class AddParticipantsEvent {
    object Init : AddParticipantsEvent()
    object Back : AddParticipantsEvent()
    object CreateOwnCard : AddParticipantsEvent()

}

data class AddParticipantsState(val gameName: String = "", val gameId: String = "")

sealed class NavigationEvent {
    private var handled: Boolean = false

    fun getValue(): NavigationEvent {
        if (handled) return Default()
        handled = true
        return this
    }

    class Default : NavigationEvent()
    class Back : NavigationEvent()
    object GoToCreateOwnCard : NavigationEvent()
}
