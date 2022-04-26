package fi.lauriari.traveljournal.screens.profile.dialogs

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

@Composable
fun ChangeProfileImageDialog(
    selectImageLauncher: ActivityResultLauncher<String>,
    profileViewModel: ProfileViewModel,
    openChangeProfileImageDialog: MutableState<Boolean>,
) {
    Dialog(
        onDismissRequest = {
            openChangeProfileImageDialog.value = false
        },
        content = {
            Column(
                modifier = Modifier
                    .size(300.dp, 300.dp)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        style = MaterialTheme.typography.h6,
                        text = "Add a Profile Image",
                        fontSize = 20.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (profileViewModel.imageUriState.value != null) {
                        Image(
                            painter = rememberImagePainter(
                                data = profileViewModel.imageUriState.value,
                                builder = {
                                    crossfade(500)
                                    transformations(
                                        CircleCropTransformation()
                                    )
                                }
                            ),
                            contentDescription = "Image of a recipe",
                            modifier = Modifier.size(125.dp)
                        )
                        Button(
                            modifier = Modifier.padding(5.dp),
                            onClick = { /*TODO*/ }
                        ) {
                            Text(text = "Upload")
                        }
                    }
                    Button(
                        modifier = Modifier.padding(vertical = 8.dp),
                        onClick = {
                            selectImageLauncher.launch("image/*")
                        },
                    ) {
                        Text("Open Gallery")
                    }
                }

            }
        }
    )
}