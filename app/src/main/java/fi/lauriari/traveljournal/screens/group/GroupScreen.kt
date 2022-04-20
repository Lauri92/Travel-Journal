package fi.lauriari.traveljournal.screens.group

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import fi.lauriari.traveljournal.AddLinkMutation
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
    val openLinkDialog = remember { mutableStateOf(false) }
    val addLinkData by groupViewModel.addGroupData.collectAsState()

    if (openLinkDialog.value) {
        AddLinkDialog(
            context = context,
            groupViewModel = groupViewModel,
            openLinkDialog = openLinkDialog,
            onAddLinkPressed = {
                groupViewModel.addLink(context)
            }
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

    Scaffold(
        content = {
            GroupScreenContent(
                groupViewModel = groupViewModel,
                navigateToProfileScreen = navigateToProfileScreen,
                getGroupByIdData = getGroupByIdData,
                openLinkDialog = openLinkDialog
            )
        }
    )
}