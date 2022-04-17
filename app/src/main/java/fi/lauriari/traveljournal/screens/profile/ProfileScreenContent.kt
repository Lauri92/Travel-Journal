package fi.lauriari.traveljournal.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.lauriari.traveljournal.GetGroupsByUserIdQuery
import fi.lauriari.traveljournal.ui.theme.backGroundBlue
import fi.lauriari.traveljournal.util.APIRequestState

@Composable
fun ProfileScreenContent(
    navigateToLoginScreen: () -> Unit,
    openDialog: () -> Unit,
    getGroupsByUserIdData: APIRequestState<GetGroupsByUserIdQuery.Data?>,

    ) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(color = backGroundBlue)
            .fillMaxSize(),
    ) {
        ProfileTopRow(
            navigateToLoginScreen = navigateToLoginScreen,
            openDialog = { openDialog() }
        )
        ProfileIndicator(context = context)
        ProfileName(context = context)

        when (getGroupsByUserIdData) {
            is APIRequestState.Loading -> {
                Column(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(
                            width = 200.dp,
                            height = 200.dp
                        ),
                        strokeWidth = 10.dp
                    )
                    Text(
                        modifier = Modifier.padding(top = 100.dp),
                        text = "Loading your groups...",
                        fontSize = 25.sp,
                    )
                }
            }
            is APIRequestState.Success -> {
                Text(getGroupsByUserIdData.response?.getGroupsByUserId.toString())
            }
            is APIRequestState.BadResponse -> {
                Text("Something went wrong loading groups!")
            }
            is APIRequestState.Idle -> {

            }
            is APIRequestState.EmptyList -> {}
        }
    }
}