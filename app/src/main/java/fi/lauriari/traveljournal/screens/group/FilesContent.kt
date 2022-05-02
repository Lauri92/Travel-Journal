package fi.lauriari.traveljournal.screens.group

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.lauriari.traveljournal.GetGroupQuery
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import coil.compose.rememberImagePainter
import fi.lauriari.traveljournal.util.Constants

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesContent(
    filesData: List<GetGroupQuery.GroupImage?>?,
    groupAdmin: String?,
    user: String
) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(128.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(filesData!!) { image ->
                Column {
                    var expanded by remember { mutableStateOf(false) }
                    val title = image?.title ?: "No title"
                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp, bottom = 0.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = title,
                            fontStyle = FontStyle.Italic,
                            fontSize = 20.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            style = TextStyle(textDecoration = TextDecoration.Underline)
                        )
                    }
                    Image(
                        painter = rememberImagePainter(
                            data = Constants.CONTAINER_BASE_URL + image?.urlStorageReference,
                            builder = {
                                crossfade(200)
                            }
                        ),
                        contentDescription = "User image",
                        modifier = Modifier
                            .padding(5.dp)
                            .size(200.dp)
                            .clickable {
                                expanded = true
                            }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        if (groupAdmin == user || user == image?.user?.id) {
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                }
                            ) {

                                Text(
                                    text = "Remove Image",
                                    color = Color.Red
                                )
                            }
                        }
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                            }
                        ) {
                            Text(
                                text = "Show bigger image",
                                color = Color.Black
                            )

                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Submitted by: ${image?.user?.username!!}",
                            fontStyle = FontStyle.Italic,
                            fontSize = 12.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    )
}
