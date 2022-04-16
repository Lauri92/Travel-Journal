package fi.lauriari.traveljournal.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fi.lauriari.traveljournal.util.User

@Composable
fun LogOutButton(
    navigateToLoginScreen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val context = LocalContext.current
        OutlinedButton(
            modifier = Modifier
                .padding(end = 10.dp, top = 10.dp)
                .align(Alignment.TopEnd),
            shape = CircleShape,
            onClick = {
                User.removeToken(context)
                User.removeUsername(context)
                navigateToLoginScreen()
            }
        ) {
            Text(
                text = "Logout",
                color = Color.Black
            )
        }
    }
}