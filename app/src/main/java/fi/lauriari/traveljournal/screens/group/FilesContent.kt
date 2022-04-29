package fi.lauriari.traveljournal.screens.group

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.lauriari.traveljournal.GetGroupQuery
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontStyle
import coil.compose.rememberImagePainter
import fi.lauriari.traveljournal.util.Constants

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesContent(
    filesData: List<GetGroupQuery.GroupImage?>?
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
                            .size(200.dp)
                    )
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
