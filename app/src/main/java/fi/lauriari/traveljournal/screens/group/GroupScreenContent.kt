package fi.lauriari.traveljournal.screens.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.ui.theme.backGroundBlue
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.viewmodels.GroupViewModel


@Composable
fun GroupScreenContent(
    navigateToProfileScreen: () -> Unit,
    groupViewModel: GroupViewModel,
    getGroupByIdData: APIRequestState<GetGroupQuery.GetGroup?>,
    openAddLinkDialog: MutableState<Boolean>,
    openAddMemberDialog: MutableState<Boolean>,
    openRemoveLinkDialog: MutableState<Boolean>,
    openModifyGroupDialog: MutableState<Boolean>,
    openDeleteGroupDialog: MutableState<Boolean>,
    openUserSelfLeaveGroupDialog: MutableState<Boolean>
) {
    val context = LocalContext.current
    val membersSelected = remember { mutableStateOf(true) }
    val linksSelected = remember { mutableStateOf(false) }
    val filesSelected = remember { mutableStateOf(false) }

    when (getGroupByIdData) {
        is APIRequestState.Success -> {
            Column(
                modifier = Modifier
                    .background(color = backGroundBlue)
                    .fillMaxSize(),
            ) {
                GroupScreenContentHeader(
                    navigateToProfileScreen = navigateToProfileScreen,
                    getGroupByIdData = getGroupByIdData,
                    openModifyGroupDialog = openModifyGroupDialog,
                    openDeleteGroupDialog = openDeleteGroupDialog,
                    openUserSelfLeaveGroupDialog = openUserSelfLeaveGroupDialog,
                    adminId = getGroupByIdData.response?.admin?.id,
                    userId = groupViewModel.userId
                )

                AddRow(
                    adminId = getGroupByIdData.response?.admin?.id,
                    groupViewModel = groupViewModel,
                    membersSelected = membersSelected,
                    linksSelected = linksSelected,
                    openAddLinkDialog = openAddLinkDialog,
                    openAddMemberDialog = openAddMemberDialog,
                )

                GroupItemsRow(
                    membersSelected = membersSelected,
                    linksSelected = linksSelected,
                    filesSelected = filesSelected
                )

                if (membersSelected.value) {
                    MembersContent(getGroupByIdData = getGroupByIdData)
                }
                if (linksSelected.value) {
                    LinksContent(
                        context = context,
                        groupViewModel = groupViewModel,
                        getGroupByIdData = getGroupByIdData,
                        openRemoveLinkDialog = openRemoveLinkDialog
                    )
                }
            }
        }
        is APIRequestState.BadResponse -> {}
        is APIRequestState.EmptyList -> {}
        is APIRequestState.Idle -> {}
    }
}