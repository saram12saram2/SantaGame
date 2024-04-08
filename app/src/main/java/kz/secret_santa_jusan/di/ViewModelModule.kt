package kz.secret_santa_jusan.di

import kz.secret_santa_jusan.presentation.add_participants.AddParticipantsViewModel
import kz.secret_santa_jusan.presentation.auth.AuthViewModel
import kz.secret_santa_jusan.presentation.auth.pass_recovery.PassRecoveryViewModel
import kz.secret_santa_jusan.presentation.create_game.CreateGameViewModel
import kz.secret_santa_jusan.presentation.example.ExampleViewModel
import kz.secret_santa_jusan.presentation.main.MainViewModel
import kz.secret_santa_jusan.presentation.my_wishlist.MyWishlistViewModel
import kz.secret_santa_jusan.presentation.registration.RegistrationViewModel
import org.koin.dsl.module

val featureExampleViewModel = module {
    factory { ExampleViewModel() }
}

val featureRegisterViewModel = module {
    factory { RegistrationViewModel(get()) }
}

val featureMainViewModel = module {
    factory { MainViewModel() }
}

val featureAuthViewModel = module {
    factory { AuthViewModel(get()) }
}

val featurePassRecoceryViewModel = module {
    factory { PassRecoveryViewModel(get()) }
}

val featureCreateGameViewModel = module {
    factory { CreateGameViewModel(get()) }
}

val featureAddParticipantsGameViewModel = module {
    factory { AddParticipantsViewModel(get()) }
}

val featureMyWishlistViewModel = module {
    factory { MyWishlistViewModel(get()) }
}