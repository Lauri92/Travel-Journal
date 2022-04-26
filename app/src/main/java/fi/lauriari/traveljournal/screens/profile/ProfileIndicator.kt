package fi.lauriari.traveljournal.screens.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.lauriari.traveljournal.util.User
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

@Composable
fun ProfileIndicator(
    context: Context,
    openChangeProfileImageDialog: () -> Unit,
    profileViewModel: ProfileViewModel
) {
    Column(
        modifier = Modifier
            .padding(top = 25.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Box(
            modifier = Modifier
                .size(125.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable {
                    openChangeProfileImageDialog()
                }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val usernameStartingLetter =
                    profileViewModel.username.get(0).toString().uppercase()
                Text(
                    text = usernameStartingLetter,
                    fontSize = 75.sp
                )
            }
        }
    }
}