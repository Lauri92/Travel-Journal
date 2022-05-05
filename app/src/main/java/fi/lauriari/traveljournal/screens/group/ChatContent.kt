package fi.lauriari.traveljournal.screens.group

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import fi.lauriari.traveljournal.data.models.UserMessage
import fi.lauriari.traveljournal.ui.theme.chatSendBackground
import fi.lauriari.traveljournal.util.Constants
import fi.lauriari.traveljournal.viewmodels.GroupViewModel
import io.socket.client.Socket
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatContent(
    groupViewModel: GroupViewModel,
    socket: Socket?
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val listState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 75.dp)
        ) {
            items(groupViewModel.messages) { message ->
                if (message.username != "" || message.message != "") {
                    MessageRow(
                        message = message,
                        user = groupViewModel.username
                    )
                }
            }
        }
        coroutineScope.launch {
            listState.animateScrollToItem(groupViewModel.messages.size)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(chatSendBackground)
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var text: String by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.weight(8f),
                value = text,
                onValueChange = {
                    text = it
                },
                placeholder = {
                    Text("Write a message..")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (text != "") {
                            socket?.emit(
                                Constants.CHAT_MESSAGE_EVENT,
                                groupViewModel.username,
                                text,
                                groupViewModel.userProfileImageUrl
                            )
                            text = ""
                            keyboardController?.hide()
                        }
                    })

            )
            IconButton(onClick = {
                if (text != "") {
                    socket?.emit(
                        Constants.CHAT_MESSAGE_EVENT,
                        groupViewModel.username,
                        text,
                        groupViewModel.userProfileImageUrl
                    )
                    text = ""
                    keyboardController?.hide()
                }
            }) {
                Icon(
                    modifier = Modifier.padding(10.dp),
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Send message",
                    tint = Color.Blue
                )
            }
            IconButton(onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(groupViewModel.messages.size)
                }
            }) {
                Icon(
                    modifier = Modifier.padding(10.dp),
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Blue
                )
            }
        }
    }
}

@Composable
fun MessageRow(
    message: UserMessage,
    user: String
) {
    if (user != message.username) {
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            if (message.userProfileImageUrl == "null") {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val usernameStartingLetter =
                            message.username[0].toString().uppercase()

                        Text(
                            text = usernameStartingLetter,
                            fontSize = 17.sp
                        )
                    }
                }
            } else {
                Image(
                    painter = rememberImagePainter(
                        data = Constants.CONTAINER_BASE_URL + message.userProfileImageUrl,
                        builder = {
                            crossfade(200)
                            transformations(
                                CircleCropTransformation()
                            )
                        }
                    ),
                    contentDescription = "User image",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(40.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = message.username,
                    fontSize = 12.sp
                )
                Text(
                    text = message.message,
                    fontSize = 15.sp
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.width(300.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = message.message,
                    fontSize = 15.sp
                )
            }
            if (message.userProfileImageUrl == "null") {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val usernameStartingLetter =
                            message.username[0].toString().uppercase()

                        Text(
                            text = usernameStartingLetter,
                            fontSize = 17.sp
                        )
                    }
                }
            } else {
                Image(
                    painter = rememberImagePainter(
                        data = Constants.CONTAINER_BASE_URL + message.userProfileImageUrl,
                        builder = {
                            crossfade(200)
                            transformations(
                                CircleCropTransformation()
                            )
                        }
                    ),
                    contentDescription = "User image",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(40.dp)
                )
            }
        }
    }
}

