package fi.lauriari.traveljournal.screens.group

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.util.APIRequestState

@Composable
fun LinksContent(
    context: Context,
    getGroupByIdData: APIRequestState.Success<GetGroupQuery.GetGroup?>
) {
    LazyColumn {
        items(getGroupByIdData.response!!.links!!.toList()) { link ->
            val uriHandler = LocalUriHandler.current
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    modifier = Modifier.clickable {
                        try {
                            uriHandler.openUri(link!!.url!!)
                        } catch (e: Exception) {
                            Toast.makeText(context, "This is not a valid Url!", Toast.LENGTH_SHORT)
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
        }
    }
}