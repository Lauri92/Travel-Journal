package fi.lauriari.traveljournal.screens.profile

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

    Scaffold(
        content = {
            ProfileScreenContent(
                navigateToLoginScreen = navigateToLoginScreen,
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
    )
}