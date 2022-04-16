package fi.lauriari.traveljournal.screens.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import fi.lauriari.traveljournal.ui.theme.backGroundBlue
import fi.lauriari.traveljournal.util.User

@Composable
fun ProfileScreenContent(navigateToLoginScreen: () -> Unit) {
    Column(
        modifier = Modifier
            .background(color = backGroundBlue)
            .fillMaxSize(),
    ) {
        Column() {
            val context = LocalContext.current
            Text(text = "Logout ${User.getUsername(context)}", color = Color.Black)
        }
    }
}