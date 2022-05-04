package fi.lauriari.traveljournal.screens.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.data.models.UserMessage
import fi.lauriari.traveljournal.ui.theme.backGroundBlue
import fi.lauriari.traveljournal.ui.theme.chatSendBackground
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.util.Constants
import fi.lauriari.traveljournal.util.Constants.CHAT_MESSAGE_EVENT
import fi.lauriari.traveljournal.viewmodels.GroupViewModel
import io.socket.client.Socket


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GroupScreenContent(
    navigateToProfileScreen: () -> Unit,
    groupViewModel: GroupViewModel,
    getGroupByIdData: APIRequestState<GetGroupQuery.GetGroup?>,
    openAddLinkDialog: MutableState<Boolean>,
    openAddMemberDialog: MutableState<Boolean>,
    openRemoveLinkDialog: MutableState<Boolean>,
    openModifyGroupDialog: MutableState<Boolean>,
    openDeleteGroupDialog: MutableState<Boolean>,
    openUserSelfLeaveGroupDialog: MutableState<Boolean>,
    openRemoveUserFromGroupDialog: MutableState<Boolean>,
    openChangeAvatarDialog: MutableState<Boolean>,
    openUploadGroupImageDialog: MutableState<Boolean>,
    openDeleteGroupImageDialog: MutableState<Boolean>,
    message: MutableState<UserMessage>,
    socket: Socket?,
) {
    val context = LocalContext.current
    val chatSelected = remember { mutableStateOf(true) }
    val membersSelected = remember { mutableStateOf(false) }
    val linksSelected = remember { mutableStateOf(false) }
    val filesSelected = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = message.value.messageId) {
        groupViewModel.messages.add(message.value)
    }

    when (getGroupByIdData) {
        is APIRequestState.Success -> {
            Column(
                modifier = Modifier
                    .background(color = backGroundBlue)
                    .fillMaxSize(),
            ) {
                GroupScreenContentHeader(
                    navigateToProfileScreen = navigateToProfileScreen,
                    getGroupByIdData = getGroupByIdData,
                    openModifyGroupDialog = openModifyGroupDialog,
                    openDeleteGroupDialog = openDeleteGroupDialog,
                    openUserSelfLeaveGroupDialog = openUserSelfLeaveGroupDialog,
                    openChangeAvatarDialog = openChangeAvatarDialog,
                    adminId = getGroupByIdData.response?.admin?.id,
                    userId = groupViewModel.userId
                )

                AddRow(
                    adminId = getGroupByIdData.response?.admin?.id,
                    groupViewModel = groupViewModel,
                    membersSelected = membersSelected,
                    linksSelected = linksSelected,
                    filesSelected = filesSelected,
                    chatSelected = chatSelected,
                    openAddLinkDialog = openAddLinkDialog,
                    openAddMemberDialog = openAddMemberDialog,
                    openUploadGroupImageDialog = openUploadGroupImageDialog
                )

                GroupItemsRow(
                    chatSelected = chatSelected,
                    membersSelected = membersSelected,
                    linksSelected = linksSelected,
                    filesSelected = filesSelected
                )

                if (membersSelected.value) {
                    MembersContent(
                        getGroupByIdData = getGroupByIdData,
                        groupViewModel = groupViewModel,
                        openRemoveUserFromGroupDialog = openRemoveUserFromGroupDialog
                    )
                }
                if (linksSelected.value) {
                    LinksContent(
                        context = context,
                        groupViewModel = groupViewModel,
                        getGroupByIdData = getGroupByIdData,
                        openRemoveLinkDialog = openRemoveLinkDialog
                    )
                }
                if (filesSelected.value) {
                    FilesContent(
                        groupAdmin = getGroupByIdData.response?.admin?.id,
                        groupViewModel = groupViewModel,
                        user = groupViewModel.userId,
                        filesData = getGroupByIdData.response?.groupImages,
                        openDeleteGroupImageDialog = openDeleteGroupImageDialog
                    )
                }
                if (chatSelected.value) {
                    val keyboardController = LocalSoftwareKeyboardController.current
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        LazyColumn(
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(chatSendBackground)
                                //.weight(1f, false)
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
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                                keyboardActions = KeyboardActions(
                                    onSend = {
                                        if (text != "") {
                                            socket?.emit(
                                                CHAT_MESSAGE_EVENT,
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
                                        CHAT_MESSAGE_EVENT,
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
                        }
                    }

                }

            }
        }
        is APIRequestState.BadResponse -> {}
        is APIRequestState.EmptyList -> {}
        is APIRequestState.Idle -> {}
        else -> {}
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

