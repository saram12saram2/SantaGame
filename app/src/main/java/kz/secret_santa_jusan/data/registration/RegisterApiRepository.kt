package kz.secret_santa_jusan.data.registration

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.secret_santa_jusan.core.network.BaseApiClient
import kz.secret_santa_jusan.core.network.KtorResponse
import kz.secret_santa_jusan.data.registration.models.TokenModel
import kz.secret_santa_jusan.data.registration.models.RegModel

class RegisterApiRepository (private val api: RegisterApiKtor): BaseApiClient() {

    suspend fun registration(registerModel: RegModel): KtorResponse<TokenModel> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                api.registration(registerModel)
            }
        }
    }
}