package fi.lauriari.traveljournal.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fi.lauriari.traveljournal.ui.theme.backGroundBlue

@Composable
fun ProfileScreenContent(
    navigateToLoginScreen: () -> Unit,
    groupNameTextState: String,
    onGroupNameTextChanged: (String) -> Unit,
    descriptionNameTextState: String,
    onDescriptionTextChanged: (String) -> Unit,
    onAddGroupPressed: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(color = backGroundBlue)
            .fillMaxSize(),
    ) {
        val openDialog = remember { mutableStateOf(true) }

        if (openDialog.value) {
            AddGroupDialog(
                context = context,
                openDialog = openDialog,
                groupNameTextState = groupNameTextState,
                onGroupNameTextChanged = onGroupNameTextChanged,
                descriptionNameTextState = descriptionNameTextState,
                onDescriptionTextChanged = onDescriptionTextChanged,
                onAddGroupPressed = onAddGroupPressed
            )
        }

        ProfileTopRow(
            navigateToLoginScreen = navigateToLoginScreen,
            openDialog = {
                openDialog.value = true
            }
        )
        ProfileIndicator(context = context)
        ProfileName(context = context)
    }
}