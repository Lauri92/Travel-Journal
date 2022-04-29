package fi.lauriari.traveljournal.screens.group.dialogs

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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

@Composable
fun UploadGroupImageDialog(
    groupViewModel: GroupViewModel,
    openUploadGroupImageDialog: MutableState<Boolean>,
    selectGroupImageLauncher: ActivityResultLauncher<String>
) {
    val context = LocalContext.current
    Dialog(
        onDismissRequest = {
            openUploadGroupImageDialog.value = false
        },
        content = {
            Column(
                modifier = Modifier
                    .size(300.dp, 400.dp)
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
                        text = "Upload image to group",
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
                    if (groupViewModel.imageUploadUriState.value != null) {
                        Image(
                            painter = rememberImagePainter(
                                data = groupViewModel.imageUploadUriState.value,
                                builder = {
                                    crossfade(500)
                                    transformations(
                                        CircleCropTransformation()
                                    )
                                }
                            ),
                            contentDescription = "Image to be uploaded",
                            modifier = Modifier.size(125.dp)
                        )
                    }
                    Button(
                        modifier = Modifier.padding(vertical = 8.dp),
                        shape = CircleShape,
                        onClick = {
                            selectGroupImageLauncher.launch("image/*")
                        },
                    ) {
                        Text("Open Gallery")
                    }
                    OutlinedTextField(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 50.dp),
                        singleLine = true,
                        label = {
                            Text("Title")
                        },
                        value = groupViewModel.groupImageUploadTitleState.value ?: "",
                        onValueChange = { newtext ->
                            groupViewModel.groupImageUploadTitleState.value = newtext
                        }
                    )
                    if (groupViewModel.imageUploadUriState.value != null) {
                        val uri = groupViewModel.imageUploadUriState.value

                        val file =
                            createTmpFileFromUri(
                                context = context,
                                uri = uri!!,
                                fileName = "hello"
                            )
                        val upload = DefaultUpload.Builder()
                            .content(file!!)
                            .build()

                        Button(
                            modifier = Modifier.padding(5.dp),
                            shape = CircleShape,
                            onClick = {
                                groupViewModel.groupImageUpload(
                                    context = context,
                                    file = upload
                                )
                                file.delete()
                                openUploadGroupImageDialog.value = false
                            }
                        ) {
                            Text(text = "Upload")
                        }
                    }
                }

            }
        }
    )
}