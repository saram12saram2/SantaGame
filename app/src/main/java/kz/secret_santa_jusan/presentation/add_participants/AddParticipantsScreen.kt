package kz.secret_santa_jusan.presentation.add_participants

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kz.secret_santa_jusan.core.views.TitleBar
import kz.secret_santa_jusan.presentation.my_wishlist.MyWishlistScreen
import kz.secret_santa_jusan.ui.theme.BrightOrange
import kz.secret_santa_jusan.ui.theme.DarkGray
import kz.secret_santa_jusan.ui.theme.PaleBlue
import kz.secret_santa_jusan.ui.theme.White

@Parcelize
class AddParticipantsScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<AddParticipantsViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            is NavigationEvent.Default -> {
            }

            NavigationEvent.GoToCreateOwnCard -> {
                navigator.push(
                    MyWishlistScreen()
                )
            }
        }
        AddParticipantsContent(viewModel = viewModel)
        viewModel.sendEvent(AddParticipantsEvent.Init)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddParticipantsPreview() {
    AddParticipantsContent(AddParticipantsEventViewModelPreview())
}

@Composable
fun AddParticipantsContent(viewModel: IAddParticipantsViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PaleBlue),
    ) {
        TitleBar()

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = (R.string.add_participants)),
                color = DarkGray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(100.dp))

            OrangeOutlineButton(
                text = stringResource(id = R.string.create_your_own_card),
                modifier = Modifier.fillMaxWidth()
            ) {
                viewModel.sendEvent(AddParticipantsEvent.CreateOwnCard)
            }
            Spacer(modifier = Modifier.height(20.dp))
            OrangeOutlineButton(
                text = stringResource(id = R.string.add_participants_manually),
                modifier = Modifier.fillMaxWidth()
            ) {

            }
            Spacer(modifier = Modifier.height(20.dp))
            OrangeOutlineButton(
                text = stringResource(id = R.string.invite_via_link),
                modifier = Modifier.fillMaxWidth()
            ) {

            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun OrangeOutlineButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Text(
        text = text,
        color = BrightOrange,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .clickable { onClick() }
            .then(modifier)
            .background(color = White, shape = RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = BrightOrange, shape = RoundedCornerShape(16.dp))
            .padding(12.dp)
    )
}

