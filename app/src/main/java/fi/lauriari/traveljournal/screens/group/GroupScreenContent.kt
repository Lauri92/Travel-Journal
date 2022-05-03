package fi.lauriari.traveljournal.screens.group

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.data.models.UserMessage
import fi.lauriari.traveljournal.ui.theme.backGroundBlue
import fi.lauriari.traveljournal.ui.theme.chatSendBackground
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.viewmodels.GroupViewModel
import io.socket.client.Socket
import org.json.JSONException
import org.json.JSONObject


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
    messages: MutableList<UserMessage>
) {
    val context = LocalContext.current
    val chatSelected = remember { mutableStateOf(true) }
    val membersSelected = remember { mutableStateOf(false) }
    val linksSelected = remember { mutableStateOf(false) }
    val filesSelected = remember { mutableStateOf(false) }

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
                    /*Column(modifier = Modifier.fillMaxSize()) {
                        messages.forEach { userMessage ->
                            Text("${userMessage.username} says: ${userMessage.message}")
                        }
                    }*/
                    var text by remember { mutableStateOf("") }

                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        //All elements
                        Column {

                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(chatSendBackground)
                                .weight(1f, false)
                                .padding(5.dp)
                        ) {
                            OutlinedTextField(
                                modifier = Modifier.weight(8f),
                                value = text,
                                onValueChange = { text = it },
                                placeholder = {
                                    Text("Write a message..")
                                }
                            )
                            IconButton(onClick = { }) {
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