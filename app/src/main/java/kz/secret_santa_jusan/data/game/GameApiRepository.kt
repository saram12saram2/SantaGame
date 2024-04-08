package kz.secret_santa_jusan.data.game

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.game.models.CreateGameModel
import kz.secret_santa_jusan.data.game.models.GameModel
import kz.secret_santa_jusan.data.registration.models.TokenModel

class GameApiRepository(private val api: GameApiKtor) : BaseApiClient() {
    suspend fun createGame(createGameModel: CreateGameModel): KtorResponse<GameModel> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.createGame(createGameModel)
            }
        }
    }
}