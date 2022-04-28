package fi.lauriari.traveljournal.screens.profile

import android.content.Context
import androidx.compose.foundation.Image
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
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import fi.lauriari.traveljournal.util.Constants.CONTAINER_BASE_URL
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
        if (profileViewModel.userImage == null) {
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
        } else {
            Image(
                painter = rememberImagePainter(
                    data = CONTAINER_BASE_URL + profileViewModel.userImage,
                    builder = {
                        crossfade(200)
                        transformations(
                            CircleCropTransformation()
                        )
                    }
                ),
                contentDescription = "User image",
                modifier = Modifier
                    .size(125.dp)
                    .clickable {
                        openChangeProfileImageDialog()
                    }
            )
        }
    }
}