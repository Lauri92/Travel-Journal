package fi.lauriari.traveljournal.screens.profile

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    navigateToLoginScreen: () -> Unit,
) {
    val context = LocalContext.current
    val groupNameTextState: String by profileViewModel.groupNameTextState
    val descriptionNameTextState: String by profileViewModel.descriptionTextState

    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AddGroupDialog(
            context = context,
            openDialog = openDialog,
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



    Scaffold(
        content = {
            ProfileScreenContent(
                navigateToLoginScreen = navigateToLoginScreen,
                openDialog = { openDialog.value = true }
            )
        }
    )
}