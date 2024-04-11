package kz.secret_santa_jusan.presentation.create_game

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.parcelize.Parcelize
import kz.secret_santa_jusan.R
import kz.secret_santa_jusan.core.base.CoreBaseScreen
import kz.secret_santa_jusan.core.views.EditText
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.presentation.add_participants.AddParticipantsScreen
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.PaleBlue

@Parcelize
class CreateGameScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<CreateGameViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            is NavigationEvent.Default -> {
            }

            NavigationEvent.GoToAddParticipants -> {
                navigator.push(
                    AddParticipantsScreen()
                )
            }
        }
        CreateGameContent(viewModel = viewModel)
        viewModel.sendEvent(CreateGameEvent.Init)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateGameContentPreview() {
    CreateGameContent(CreateGameViewModelPreview())
}

@Composable
fun CreateGameContent(viewModel: ICreateGameViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PaleBlue)
    ) {
        TitleBar()

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = (R.string.create_game)),
                color = DarkGray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            EditText(
                value = state.gameName,
                onValueChange = { name ->
                    viewModel.sendEvent(CreateGameEvent.EnterGameName(name))
                },
                enabled = true,
                isError = false,
                modifier = Modifier
                    .fillMaxWidth(),
                label = stringResource(R.string.game_name)
            )
            Spacer(modifier = Modifier.height(16.dp))
            EditText(
                value = state.gameId,
                onValueChange = { id ->
                    viewModel.sendEvent(CreateGameEvent.EnterGameId(id))
                },
                enabled = true,
                isError = false,
                modifier = Modifier
                    .fillMaxWidth(),
                label = stringResource(R.string.game_id)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.sendEvent(CreateGameEvent.CreateGame)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(BrightOrange)
            ) {
                Text(
                    text = stringResource(id = R.string.create_game),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

    }
}
