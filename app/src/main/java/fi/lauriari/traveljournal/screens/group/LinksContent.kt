package fi.lauriari.traveljournal.screens.group

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.viewmodels.GroupViewModel

@Composable
fun LinksContent(
    context: Context,
    groupViewModel: GroupViewModel,
    getGroupByIdData: APIRequestState.Success<GetGroupQuery.GetGroup?>
) {
    LazyColumn {
        items(getGroupByIdData.response!!.links!!.toList()) { link ->
            val uriHandler = LocalUriHandler.current
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black)
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(8f)
                ) {
                    Text(
                        modifier = Modifier
                            .clickable {
                                try {
                                    uriHandler.openUri(link!!.url!!)
                                } catch (e: Exception) {
                                    Toast
                                        .makeText(
                                            context,
                                            "This is not a valid Url!",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            },
                        text = link!!.url!!,
                        fontSize = 17.sp,
                        color = Color.Blue
                    )
                    Text(
                        text = "Submitted by: ${link.user!!.username}",
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (link?.user?.id == groupViewModel.userId ||
                        getGroupByIdData.response.admin?.id == groupViewModel.userId
                    ) {
                        IconButton(
                            onClick = {

                            }) {
                            Icon(
                                modifier = Modifier.padding(10.dp),
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Admin indicator",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}