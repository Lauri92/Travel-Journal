package fi.lauriari.traveljournal.screens.profile.dialogs

import android.content.Context
import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toFile
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.apollographql.apollo3.api.DefaultUpload
import com.apollographql.apollo3.api.content
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel
import okio.use
import org.apache.commons.io.FileUtils
import java.io.File


@Composable
fun ChangeProfileImageDialog(
    selectImageLauncher: ActivityResultLauncher<String>,
    profileViewModel: ProfileViewModel,
    openChangeProfileImageDialog: MutableState<Boolean>,
) {
    val context = LocalContext.current
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
                        val uri = profileViewModel.imageUriState.value

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
                            onClick = {
                                profileViewModel.profilePictureUpload(
                                    context = context,
                                    file = upload
                                )
                                file.delete()
                                openChangeProfileImageDialog.value = false
                            }
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

fun createTmpFileFromUri(
    context: Context,
    uri: Uri,
    fileName: String
): File? {
    return try {
        val stream = context.contentResolver.openInputStream(uri)
        val file = File.createTempFile(fileName, "jpg", context.cacheDir)
        FileUtils.copyInputStreamToFile(stream, file)
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}