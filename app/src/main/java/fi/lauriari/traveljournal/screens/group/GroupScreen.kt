package fi.lauriari.traveljournal.screens.group

import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import fi.lauriari.traveljournal.AddLinkMutation
import fi.lauriari.traveljournal.AddUserToGroupMutation
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.viewmodels.GroupViewModel

@Composable
fun GroupScreen(
    navigateToProfileScreen: () -> Unit,
    groupViewModel: GroupViewModel,
    getGroupByIdData: APIRequestState<GetGroupQuery.GetGroup?>
) {

    val context = LocalContext.current
    val openAddLinkDialog = remember { mutableStateOf(false) }
    val openAddMemberDialog = remember { mutableStateOf(false) }
    val addLinkData by groupViewModel.addLinkData.collectAsState()
    val removeLinkData by groupViewModel.removeLinkData.collectAsState()
    val addUserToGroupData by groupViewModel.addUserToGroupData.collectAsState()
    val searchUsersData by groupViewModel.searchUsersData.collectAsState()

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



    when (val data: APIRequestState<AddLinkMutation.AddInfoLink?> = addLinkData) {
        is APIRequestState.Loading -> {

        }
        is APIRequestState.Success -> {
            AlertDialog(
                onDismissRequest = {
                    groupViewModel.setAddLinkDataIdle()
                },
                title = {
                    Text(text = "Added a link: ${data.response?.url}")
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
            groupViewModel.getGroupById(context = context)
            groupViewModel.setAddLinkDataIdle()
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
        is APIRequestState.EmptyList -> {}
        is APIRequestState.Idle -> {}
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

    Scaffold(
        content = {
            GroupScreenContent(
                groupViewModel = groupViewModel,
                navigateToProfileScreen = navigateToProfileScreen,
                getGroupByIdData = getGroupByIdData,
                openAddLinkDialog = openAddLinkDialog,
                openAddMemberDialog = openAddMemberDialog
            )
        }
    )
}