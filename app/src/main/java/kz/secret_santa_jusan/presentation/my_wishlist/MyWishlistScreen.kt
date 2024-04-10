package kz.secret_santa_jusan.presentation.my_wishlist

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import kz.secret_santa_jusan.ui.theme.Gray
import kz.secret_santa_jusan.ui.theme.PaleBlue

@Parcelize
class MyWishlistScreen : CoreBaseScreen(), Parcelable {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<MyWishlistViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        // You might want to track navigation events similarly to the MainScreen
        // ...

        val navigationEvent =
            viewModel.navigationEvent.collectAsStateWithLifecycle().value.getValue()
        when (navigationEvent) {
            is NavigationEvent.Default -> {}
            is NavigationEvent.Back -> navigator.pop()
            //is NavigationEvent.AuthRouter -> navigator.push(ScreenRegistry.get(AuthRouter.ProfileScreen()))
            is NavigationEvent.Default -> {
            }

            NavigationEvent.GoToAddParticipants -> {
                navigator.push(
                    AddParticipantsScreen()
                )
            }
        }
        CreateGameContent(viewModel = viewModel)
        viewModel.sendEvent(MyWishlistEvent.Init)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyWishlistScreenContentPreview() {
    CreateGameContent(MyWishlistViewModelPreview())
}

@Composable
fun CreateGameContent(viewModel: IMyWishlistViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PaleBlue)
    ) {
        // Reuse the TitleBar or create a specific one for this page
        TitleBar()

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = stringResource(id = (R.string.my_wishlist)),
                color = DarkGray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = (R.string.my_wishlist_desc)),
                color = DarkGray,
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(50.dp))

            LazyColumn {
                itemsIndexed(state.gifts) { index, gift ->
                    EditText(
                        value = gift,
                        onValueChange = { value ->
                            viewModel.sendEvent(MyWishlistEvent.EnterGift(value, index))
                        },
                        enabled = true,
                        isError = false,
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = stringResource(R.string.gift_no, (index+1))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                viewModel.sendEvent(MyWishlistEvent.AddAnotherGift)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier
                        .background(color = BrightOrange, shape = CircleShape)
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.add_another_gift))
            }
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.sendEvent(MyWishlistEvent.CreateMyWishlist)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(BrightOrange)
            ) {
                Text(
                    text = stringResource(id = R.string.next), // Button text string resource id
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = (R.string.my_wishlist_info)),
                color = Gray,
                textAlign = TextAlign.Center,
                fontSize = 8.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

    }
}

// Define the view model for the Invitation Screen
// ...

// Rest of the code structure follows your original MainScreen code.
