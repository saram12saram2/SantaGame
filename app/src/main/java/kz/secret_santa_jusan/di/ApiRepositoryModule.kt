package kz.secret_santa_jusan.di

import kz.secret_santa_jusan.data.auth.AuthApiRepository
import kz.secret_santa_jusan.data.example.ExampleApiKtor
import kz.secret_santa_jusan.data.example.ExampleApiRepository
import kz.secret_santa_jusan.data.game.GameApiRepository
import kz.secret_santa_jusan.data.recovery_pass.PassRecoveryApiRepository
import kz.secret_santa_jusan.data.registration.RegisterApiRepository
import kz.secret_santa_jusan.data.wishlist.WishlistApiRepository
import org.koin.dsl.module

val dataExampleApiKtorModule = module {
    single { ExampleApiRepository(get()) }
}

val dataRegisterApiKtorModule = module {
    single { RegisterApiRepository(get()) }
}

val dataAuthApiKtorModule = module {
    single { AuthApiRepository(get()) }
}

val dataPassRecoceryApiKtorModule = module {
    single { PassRecoveryApiRepository(get()) }
}

val dataGameApiKtorModule = module {
    single { GameApiRepository(get()) }
}

val dataWishListApiKtorModule = module {
    single { WishlistApiRepository(get()) }
}