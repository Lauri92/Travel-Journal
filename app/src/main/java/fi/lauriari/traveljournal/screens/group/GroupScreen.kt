package fi.lauriari.traveljournal.screens.group

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
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