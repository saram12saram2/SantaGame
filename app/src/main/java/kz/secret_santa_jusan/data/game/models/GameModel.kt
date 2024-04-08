package kz.secret_santa_jusan.data.game.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameModel(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("maxPrice")
    val maxPrice: Int? = null,
    @SerialName("participantCount")
    val participantCount: Int? = null,
    @SerialName("creatorId")
    val creatorId: String? = null,
    @SerialName("role")
    val role: String? = null,
)