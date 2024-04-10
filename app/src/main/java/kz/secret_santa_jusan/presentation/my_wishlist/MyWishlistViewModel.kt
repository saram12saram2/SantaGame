package kz.secret_santa_jusan.presentation.my_wishlist

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.secret_santa_jusan.core.base.CoreBaseViewModel
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.data.wishlist.WishlistApiRepository
import kz.secret_santa_jusan.data.wishlist.models.CreateWishlistModel
import trikita.log.Log

class MyWishlistViewModel(
    private val repository: WishlistApiRepository
) : CoreBaseViewModel(), IMyWishlistViewModel {
    private val _state = MutableStateFlow(MyWishlistState())
    override val state: StateFlow<MyWishlistState> = _state.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.Default())
    override val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    override fun sendEvent(event: MyWishlistEvent) {
        when (event) {
            is MyWishlistEvent.Init -> {
            }

            is MyWishlistEvent.Back -> {
                _navigationEvent.value = NavigationEvent.Back()
            }

            is MyWishlistEvent.CreateMyWishlist -> {
                createWishlist()
            }

            MyWishlistEvent.AddAnotherGift -> {
                _state.update {
                    it.copy(gifts = ArrayList(it.gifts).apply {
                        add("")
                    })
                }
            }

            is MyWishlistEvent.EnterGift -> {
                _state.update {
                    val list = ArrayList(it.gifts).apply {
                        this[event.index] = event.text
                    }
                    it.copy(gifts = list)
                }
            }

            else -> {}
        }
    }


    private fun createWishlist() = with(state.value){
        screenModelScope.launch {
            repository.createWishlist(CreateWishlistModel(
                gameId = GlobalStorage.getGameId(),
                gifts = gifts
            )).apply {
                if(isSuccessful) {
                    Log.d("ok", "ok")
                }
            }
        }
    }
}

interface IMyWishlistViewModel {
    val state: StateFlow<MyWishlistState>
    val navigationEvent: StateFlow<NavigationEvent>
    fun sendEvent(event: MyWishlistEvent)
}

class MyWishlistViewModelPreview : IMyWishlistViewModel {
    override val state: StateFlow<MyWishlistState> = MutableStateFlow(
        MyWishlistState()
    ).asStateFlow()
    override val navigationEvent = MutableStateFlow(NavigationEvent.Default()).asStateFlow()
    override fun sendEvent(event: MyWishlistEvent) {}
}


sealed class MyWishlistEvent {
    object Init : MyWishlistEvent()
    object Back : MyWishlistEvent()
    object CreateMyWishlist : MyWishlistEvent()
    object AddAnotherGift : MyWishlistEvent()

    class EnterGift(val text: String, val index: Int) : MyWishlistEvent()
}

data class MyWishlistState(val gifts: List<String> = arrayListOf(""))

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
