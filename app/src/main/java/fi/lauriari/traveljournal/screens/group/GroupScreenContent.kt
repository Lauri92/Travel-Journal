package fi.lauriari.traveljournal.screens.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.data.models.UserMessage
import fi.lauriari.traveljournal.ui.theme.backGroundBlue
import fi.lauriari.traveljournal.util.APIRequestState
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
                    groupViewModel = groupViewModel,
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
                    ChatContent(
                        groupViewModel = groupViewModel,
                        socket = socket
                    )
                }
            }
        }
        is APIRequestState.BadResponse -> {}
        is APIRequestState.EmptyList -> {}
        is APIRequestState.Idle -> {}
        else -> {}
    }
}