package kz.secret_santa_jusan.data.game.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateGameModel(
    @SerialName("name")
    val name: String? = null,
    @SerialName("maxPrice")
    val maxPrice: Int? = null,
    @SerialName("uniqueIdentifier")
    val uniqueIdentifier: String? = null,
    @SerialName("priceLimitChecked")
    val priceLimitChecked: Boolean? = null
)