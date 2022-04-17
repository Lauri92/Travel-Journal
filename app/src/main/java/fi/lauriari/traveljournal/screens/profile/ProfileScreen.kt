package fi.lauriari.traveljournal.screens.profile

import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.util.User
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    navigateToLoginScreen: () -> Unit,
) {
    val context = LocalContext.current

    val groupNameTextState: String by profileViewModel.groupNameTextState
    val descriptionNameTextState: String by profileViewModel.descriptionTextState

    val getGroupsByUserIdData by profileViewModel.getGroupsByUserIdData.collectAsState()
    val getAddGroupData by profileViewModel.addGroupData.collectAsState()

    val openAddGroupDialog = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = User.getToken(context)) {
        profileViewModel.getGroupsByUserId(context)
        profileViewModel.username = User.getUsername(context).toString()
        profileViewModel.userId = User.getUserId(context).toString()
    }


    if (openAddGroupDialog.value) {
        AddGroupDialog(
            context = context,
            openDialog = openAddGroupDialog,
            groupNameTextState = groupNameTextState,
            onGroupNameTextChanged = { newGroupNameText ->
                profileViewModel.groupNameTextState.value = newGroupNameText
            },
            descriptionNameTextState = descriptionNameTextState,
            onDescriptionTextChanged = { newDescriptionText ->
                profileViewModel.descriptionTextState.value = newDescriptionText
            },
            onAddGroupPressed = {
                profileViewModel.addGroup(context)
            }
        )
    }

    when (getAddGroupData) {
        is APIRequestState.Loading -> {}
        is APIRequestState.Success -> {}
        is APIRequestState.BadResponse -> {}
        is APIRequestState.Idle -> {}
        is APIRequestState.EmptyList -> {}
        is APIRequestState.Error -> {}
    }



    Scaffold(
        content = {
            ProfileScreenContent(
                profileViewModel = profileViewModel,
                navigateToLoginScreen = navigateToLoginScreen,
                openDialog = { openAddGroupDialog.value = true },
                getGroupsByUserIdData = getGroupsByUserIdData
            )
        }
    )
}