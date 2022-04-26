package fi.lauriari.traveljournal.screens.profile

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import fi.lauriari.traveljournal.AddGroupMutation
import fi.lauriari.traveljournal.screens.profile.dialogs.AddGroupDialog
import fi.lauriari.traveljournal.screens.profile.dialogs.ChangeProfileImageDialog
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.util.User
import fi.lauriari.traveljournal.viewmodels.GroupViewModel
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    groupViewModel: GroupViewModel,
    navigateToLoginScreen: () -> Unit,
    navigateToGroupScreen: (String) -> Unit,
) {
    val context = LocalContext.current

    val getGroupsByUserIdData by profileViewModel.getGroupsByUserIdData.collectAsState()
    val getAddGroupData by profileViewModel.addGroupData.collectAsState()

    val openAddGroupDialog = remember { mutableStateOf(false) }
    val openChangeProfileImageDialog = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = User.getToken(context)) {
        profileViewModel.getGroupsByUserId(context)
        profileViewModel.username = User.getUsername(context).toString()
        profileViewModel.userId = User.getUserId(context).toString()
        groupViewModel.userId = User.getUserId(context).toString()
    }


    if (openAddGroupDialog.value) {
        AddGroupDialog(
            context = context,
            profileViewModel = profileViewModel,
            openAddGroupDialog = openAddGroupDialog,
            onAddGroupPressed = {
                profileViewModel.addGroup(context)
            }
        )
    }
    if (openChangeProfileImageDialog.value) {
        ChangeProfileImageDialog(
            openChangeProfileImageDialog = openChangeProfileImageDialog
        )
    }

    when (val data: APIRequestState<AddGroupMutation.AddGroup?> = getAddGroupData) {
        is APIRequestState.Loading -> {}
        is APIRequestState.Success -> {
            AlertDialog(
                onDismissRequest = {
                    profileViewModel.setAddGroupDataIdle()
                },
                title = {
                    Text(text = "Group added. Added: ${data.response?.name}")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            profileViewModel.setAddGroupDataIdle()
                        }) {
                        Text("OK")
                    }
                },
            )
            profileViewModel.getGroupsByUserId(context)
        }
        is APIRequestState.BadResponse -> {
            AlertDialog(
                onDismissRequest = {
                    profileViewModel.setAddGroupDataIdle()
                },
                title = {
                    Text(text = "Failed to add a group. Minimum group name and description length is 3 characters")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            profileViewModel.setAddGroupDataIdle()
                        }) {
                        Text("OK")
                    }
                },
            )
        }
        is APIRequestState.Idle -> {}
        is APIRequestState.EmptyList -> {}
    }

    Scaffold(
        content = {
            ProfileScreenContent(
                profileViewModel = profileViewModel,
                navigateToLoginScreen = navigateToLoginScreen,
                navigateToGroupScreen = navigateToGroupScreen,
                openAddGroupDialog = { openAddGroupDialog.value = true },
                openChangeProfileImageDialog = { openChangeProfileImageDialog.value = true },
                getGroupsByUserIdData = getGroupsByUserIdData,
            )
        }
    )
}