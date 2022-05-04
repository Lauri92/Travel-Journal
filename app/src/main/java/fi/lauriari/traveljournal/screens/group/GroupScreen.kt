package fi.lauriari.traveljournal.screens.group

import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import fi.lauriari.traveljournal.AddLinkMutation
import fi.lauriari.traveljournal.AddUserToGroupMutation
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.UpdateGroupMutation
import fi.lauriari.traveljournal.data.models.UserMessage
import fi.lauriari.traveljournal.screens.group.dialogs.*
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.viewmodels.GroupViewModel
import io.socket.client.Socket

@Composable
fun GroupScreen(
    navigateToProfileScreen: () -> Unit,
    groupViewModel: GroupViewModel,
    getGroupByIdData: APIRequestState<GetGroupQuery.GetGroup?>,
    selectAvatarLauncher: ActivityResultLauncher<String>,
    selectGroupImageLauncher: ActivityResultLauncher<String>,
    message: MutableState<UserMessage>,
    socket: Socket?,
) {
    val context = LocalContext.current
    val sendMessageTextState: String by groupViewModel.sendMessageTextState

    val openAddLinkDialog = remember { mutableStateOf(false) }
    val openAddMemberDialog = remember { mutableStateOf(false) }
    val openRemoveLinkDialog = remember { mutableStateOf(false) }
    val openModifyGroupDialog = remember { mutableStateOf(false) }
    val openDeleteGroupDialog = remember { mutableStateOf(false) }
    val openUserSelfLeaveGroupDialog = remember { mutableStateOf(false) }
    val openRemoveUserFromGroupDialog = remember { mutableStateOf(false) }
    val openChangeAvatarDialog = remember { mutableStateOf(false) }
    val openUploadGroupImageDialog = remember { mutableStateOf(false) }
    val openDeleteGroupImageDialog = remember { mutableStateOf(false) }

    val addLinkData by groupViewModel.addLinkData.collectAsState()
    val removeLinkData by groupViewModel.removeLinkData.collectAsState()
    val addUserToGroupData by groupViewModel.addUserToGroupData.collectAsState()
    val searchUsersData by groupViewModel.searchUsersData.collectAsState()
    val updateGroupData by groupViewModel.updateGroupData.collectAsState()
    val deleteGroupData by groupViewModel.deleteGroupData.collectAsState()
    val userSelfLeaveGroupData by groupViewModel.userSelfLeaveGroupData.collectAsState()
    val removeUserFromGroupData by groupViewModel.removeUserFromGroupData.collectAsState()
    val groupAvatarUploadData by groupViewModel.groupAvatarUploadData.collectAsState()
    val groupImageUploadData by groupViewModel.groupImageUploadData.collectAsState()
    val groupImageDeleteData by groupViewModel.groupImageDeleteData.collectAsState()

    if (openAddLinkDialog.value) {
        AddLinkDialog(
            context = context,
            groupViewModel = groupViewModel,
            openAddLinkDialog = openAddLinkDialog,
            onAddLinkPressed = {
                groupViewModel.addLink(context)
            }
        )
    }
    if (openAddMemberDialog.value) {
        AddMemberDialog(
            context = context,
            groupViewModel = groupViewModel,
            openAddMemberDialog = openAddMemberDialog,
            onSearchMembersPressed = {
                groupViewModel.searchUsers(context)
            },
            searchUsersData = searchUsersData,
        )
    }
    if (openRemoveLinkDialog.value) {
        RemoveLinkDialog(
            openRemoveLinkDialog = openRemoveLinkDialog,
            onRemoveLinkPressed = {
                groupViewModel.removeLink(context)
            }
        )
    }
    if (openModifyGroupDialog.value) {
        ModifyGroupDialog(
            groupViewModel = groupViewModel,
            openModifyGroupDialog = openModifyGroupDialog,
            onUpdateGroupPressed = {
                groupViewModel.updateGroup(context = context)
            }

        )
    }
    if (openDeleteGroupDialog.value) {
        DeleteGroupDialog(
            openDeleteGroupDialog = openDeleteGroupDialog,
            onDeleteGroupPressed = {
                groupViewModel.deleteGroup(context = context)
            })
    }
    if (openUserSelfLeaveGroupDialog.value) {
        UserSelfLeaveGroupDialog(
            openSelfLeaveGroupDialog = openUserSelfLeaveGroupDialog,
            onLeaveGroupPressed = {
                groupViewModel.userSelfLeaveGroup(context = context)
            }
        )
    }
    if (openRemoveUserFromGroupDialog.value) {
        RemoveUserFromGroupDialog(
            openRemoveFromUserGroupDialog = openRemoveUserFromGroupDialog,
            onRemoveUserPressed = {
                groupViewModel.removeUserFromGroup(context = context)
            })
    }
    if (openChangeAvatarDialog.value) {
        ChangeGroupAvatarDialog(
            selectAvatarLauncher = selectAvatarLauncher,
            groupViewModel = groupViewModel,
            openChangeGroupAvatar = openChangeAvatarDialog
        )
    }
    if (openUploadGroupImageDialog.value) {
        UploadGroupImageDialog(
            groupViewModel = groupViewModel,
            openUploadGroupImageDialog = openUploadGroupImageDialog,
            selectGroupImageLauncher = selectGroupImageLauncher
        )
    }
    if (openDeleteGroupImageDialog.value) {
        DeleteGroupImageDialog(
            openDeleteGroupImageDialog = openDeleteGroupImageDialog,
            onDeleteImagePressed = {
                groupViewModel.groupImageDelete(context = context)
            }
        )
    }

    when (val data: APIRequestState<AddLinkMutation.AddInfoLink?> = addLinkData) {
        is APIRequestState.Success -> {
            AlertDialog(
                onDismissRequest = {
                    groupViewModel.setAddLinkDataIdle()
                    groupViewModel.getGroupById(context = context)
                },
                title = {
                    Text(text = "Added a link: ${data.response?.url}")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            groupViewModel.setAddLinkDataIdle()
                            groupViewModel.getGroupById(context = context)
                        }) {
                        Text("OK")
                    }
                },
            )
        }
        is APIRequestState.BadResponse -> {
            AlertDialog(
                onDismissRequest = {
                    groupViewModel.setAddLinkDataIdle()
                },
                title = {
                    Text(text = "Failed to add a link.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            groupViewModel.setAddLinkDataIdle()
                        }) {
                        Text("OK")
                    }
                },
            )
        }
        else -> {}
    }
    when (val data: APIRequestState<String?> = removeLinkData) {
        is APIRequestState.Success -> {
            Toast.makeText(context, "Removed link", Toast.LENGTH_SHORT).show()
            groupViewModel.getGroupById(context = context)
            groupViewModel.setRemoveLinkDataIdle()
        }
        is APIRequestState.BadResponse -> {
            Toast.makeText(context, data.error, Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }
    when (val data: APIRequestState<AddUserToGroupMutation.AddUserToGroup?> = addUserToGroupData) {
        is APIRequestState.Loading -> {}
        is APIRequestState.Success -> {
            groupViewModel.getGroupById(context = context)
            groupViewModel.setAddUserToGroupDataIdle()
        }
        is APIRequestState.BadResponse -> {}
        is APIRequestState.EmptyList -> {}
        is APIRequestState.Idle -> {}
    }
    when (val data: APIRequestState<UpdateGroupMutation.UpdateGroup?> = updateGroupData) {
        is APIRequestState.Success -> {
            groupViewModel.getGroupById(context)
            groupViewModel.setUpdateGroupDataIdle()
        }
        is APIRequestState.BadResponse -> {
            Toast.makeText(context, "Failed to update group", Toast.LENGTH_SHORT).show()
            groupViewModel.setUpdateGroupDataIdle()
        }
        else -> {

        }
    }
    when (val data: APIRequestState<String?> = deleteGroupData) {
        is APIRequestState.Success -> {
            groupViewModel.setDeleteUserDataIdle()
            navigateToProfileScreen()
        }
        is APIRequestState.BadResponse -> {
            Toast.makeText(context, "Failed to delete group!", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }
    when (val data: APIRequestState<String?> = userSelfLeaveGroupData) {
        is APIRequestState.Success -> {
            groupViewModel.setUserSelfLeaveGroupDataIdle()
            navigateToProfileScreen()
        }
        is APIRequestState.BadResponse -> {
            Toast.makeText(context, "Failed to leave the group!", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }
    when (val data: APIRequestState<String?> = removeUserFromGroupData) {
        is APIRequestState.Success -> {
            groupViewModel.getGroupById(context = context)
            groupViewModel.setRemoveUserFromGroupDataIdle()
        }
        is APIRequestState.BadResponse -> {
            Toast.makeText(context, "Failed to remove user from group", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }
    when (val data: APIRequestState<String?> = groupAvatarUploadData) {
        is APIRequestState.Success -> {
            groupViewModel.getGroupById(context)
            groupViewModel.setGroupAvatarUploadDataIdle()
        }
        is APIRequestState.BadResponse -> {
            Toast.makeText(context, "Failed to upload avatar", Toast.LENGTH_SHORT).show()
            groupViewModel.setGroupAvatarUploadDataIdle()
        }
        else -> {}
    }
    when (val data: APIRequestState<String?> = groupImageUploadData) {
        is APIRequestState.Success -> {
            groupViewModel.getGroupById(context)
            groupViewModel.setGroupImageUploadDataIdle()
        }
        is APIRequestState.BadResponse -> {
            Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show()
            groupViewModel.setGroupImageUploadDataIdle()
        }
        else -> {}
    }
    when (val data: APIRequestState<String?> = groupImageDeleteData) {
        is APIRequestState.Success -> {
            groupViewModel.getGroupById(context)
            groupViewModel.setGroupImageDeleteDataIdle()
        }
        is APIRequestState.BadResponse -> {
            Toast.makeText(context, "Failed to delete image!", Toast.LENGTH_SHORT).show()
            groupViewModel.setGroupImageDeleteDataIdle()
        }
        else -> {

        }
    }

    Scaffold(
        content = {
            GroupScreenContent(
                groupViewModel = groupViewModel,
                navigateToProfileScreen = navigateToProfileScreen,
                getGroupByIdData = getGroupByIdData,
                message = message,
                socket = socket,
                openAddLinkDialog = openAddLinkDialog,
                openAddMemberDialog = openAddMemberDialog,
                openRemoveLinkDialog = openRemoveLinkDialog,
                openModifyGroupDialog = openModifyGroupDialog,
                openDeleteGroupDialog = openDeleteGroupDialog,
                openUserSelfLeaveGroupDialog = openUserSelfLeaveGroupDialog,
                openRemoveUserFromGroupDialog = openRemoveUserFromGroupDialog,
                openChangeAvatarDialog = openChangeAvatarDialog,
                openUploadGroupImageDialog = openUploadGroupImageDialog,
                openDeleteGroupImageDialog = openDeleteGroupImageDialog,
                sendMessageTextState = sendMessageTextState,
                onNewMessageTextStateChanged = { newText ->
                    groupViewModel.sendMessageTextState.value = newText
                }
            )
        }
    )
}