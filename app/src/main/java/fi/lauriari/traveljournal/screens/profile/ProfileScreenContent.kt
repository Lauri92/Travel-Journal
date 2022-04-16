package fi.lauriari.traveljournal.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.lauriari.traveljournal.ui.theme.backGroundBlue
import fi.lauriari.traveljournal.util.User

@Composable
fun ProfileScreenContent(
    navigateToLoginScreen: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(color = backGroundBlue)
            .fillMaxSize(),
    ) {
        LogOutButton(
            navigateToLoginScreen = navigateToLoginScreen
        )
        ProfileIndicator(context = context)
        ProfileName(context = context)
    }
}