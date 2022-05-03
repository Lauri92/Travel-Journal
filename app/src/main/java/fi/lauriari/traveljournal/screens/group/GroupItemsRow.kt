package fi.lauriari.traveljournal.screens.group

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GroupItemsRow(
    chatSelected: MutableState<Boolean>,
    membersSelected: MutableState<Boolean>,
    linksSelected: MutableState<Boolean>,
    filesSelected: MutableState<Boolean>,
) {
    var chatTextDecoration: TextDecoration = TextDecoration.None
    var membersTextDecoration: TextDecoration = TextDecoration.None
    var linksTextDecoration: TextDecoration = TextDecoration.None
    var filesTextDecoration: TextDecoration = TextDecoration.None

    if (chatSelected.value) {
        chatTextDecoration = TextDecoration.Underline
    }
    if (membersSelected.value) {
        membersTextDecoration = TextDecoration.Underline
    }
    if (linksSelected.value) {
        linksTextDecoration = TextDecoration.Underline
    }
    if (filesSelected.value) {
        filesTextDecoration = TextDecoration.Underline
    }

    Row(
        modifier = Modifier
            .border(width = 1.dp, color = Color.Gray)
            .padding(15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.clickable {
                chatSelected.value = true
                membersSelected.value = false
                linksSelected.value = false
                filesSelected.value = false
            },
            text = "Chat",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            style = TextStyle(textDecoration = chatTextDecoration)
        )
        Text(
            modifier = Modifier.clickable {
                chatSelected.value = false
                membersSelected.value = true
                linksSelected.value = false
                filesSelected.value = false
            },
            text = "Members",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            style = TextStyle(textDecoration = membersTextDecoration)
        )
        Text(
            modifier = Modifier.clickable {
                chatSelected.value = false
                membersSelected.value = false
                linksSelected.value = true
                filesSelected.value = false
            },
            text = "Links",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            style = TextStyle(textDecoration = linksTextDecoration)
        )
        Text(
            modifier = Modifier.clickable {
                chatSelected.value = false
                membersSelected.value = false
                linksSelected.value = false
                filesSelected.value = true
            },
            text = "Files",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            style = TextStyle(textDecoration = filesTextDecoration)
        )
    }
}