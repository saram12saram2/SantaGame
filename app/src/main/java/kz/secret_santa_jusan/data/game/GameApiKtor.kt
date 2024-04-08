package kz.secret_santa_jusan.data.game

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.data.game.models.CreateGameModel

class GameApiKtor(private val httpClient: HttpClient) {

    suspend fun createGame(model: CreateGameModel): HttpResponse {
        return httpClient.post("games/create-game") {
            header("Authorization", "Bearer ${GlobalStorage.access_token}")
            setBody(model)
        }
    }
}