package fi.lauriari.traveljournal.screens.group.dialogs

import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.apollographql.apollo3.api.DefaultUpload
import com.apollographql.apollo3.api.content
import fi.lauriari.traveljournal.screens.profile.dialogs.createTmpFileFromUri
import fi.lauriari.traveljournal.viewmodels.GroupViewModel
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

@Composable
fun ChangeGroupAvatarDialog(
    selectAvatarLauncher: ActivityResultLauncher<String>,
    groupViewModel: GroupViewModel,
    openChangeGroupAvatar: MutableState<Boolean>
) {
    val context = LocalContext.current
    Dialog(
        onDismissRequest = {
            openChangeGroupAvatar.value = false
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
                        text = "Change group avatar",
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
                    if (groupViewModel.avatarUriState.value != null) {
                        val uri = groupViewModel.avatarUriState.value

                        val file =
                            createTmpFileFromUri(
                                context = context,
                                uri = uri!!,
                                fileName = "hello"
                            )
                        val upload = DefaultUpload.Builder()
                            .content(file!!)
                            .build()

                        Image(
                            painter = rememberImagePainter(
                                data = groupViewModel.avatarUriState.value,
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
                            onClick = {
                                groupViewModel.groupAvatarUpload(
                                    context = context,
                                    file = upload
                                )
                                file.delete()
                                openChangeGroupAvatar.value = false
                            }
                        ) {
                            Text(text = "Upload")
                        }
                    }
                    Button(
                        modifier = Modifier.padding(vertical = 8.dp),
                        onClick = {
                            selectAvatarLauncher.launch("image/*")
                        },
                    ) {
                        Text("Open Gallery")
                    }
                }

            }
        }
    )
}