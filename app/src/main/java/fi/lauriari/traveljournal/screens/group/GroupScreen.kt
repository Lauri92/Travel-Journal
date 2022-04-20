package fi.lauriari.traveljournal.screens.group

import androidx.compose.material.*
import androidx.compose.runtime.*
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.viewmodels.GroupViewModel

@Composable
fun GroupScreen(
    navigateToProfileScreen: () -> Unit,
    groupViewModel: GroupViewModel,
    getGroupByIdData: APIRequestState<GetGroupQuery.GetGroup?>
) {

    val openLinkDialog = remember { mutableStateOf(false) }

    if (openLinkDialog.value) {
        AddLinkDialog(
            openLinkDialog = openLinkDialog
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