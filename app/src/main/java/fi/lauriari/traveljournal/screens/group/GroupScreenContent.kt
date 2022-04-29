package fi.lauriari.traveljournal.screens.group

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.ui.theme.backGroundBlue
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.util.Constants
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
    openUserSelfLeaveGroupDialog: MutableState<Boolean>,
    openRemoveUserFromGroupDialog: MutableState<Boolean>,
    openChangeAvatarDialog: MutableState<Boolean>,
    openUploadGroupImageDialog: MutableState<Boolean>
) {
    val context = LocalContext.current
    val membersSelected = remember { mutableStateOf(false) }
    val linksSelected = remember { mutableStateOf(false) }
    val filesSelected = remember { mutableStateOf(true) }

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
                    openChangeAvatarDialog = openChangeAvatarDialog,
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
                    openUploadGroupImageDialog = openUploadGroupImageDialog
                )

                GroupItemsRow(
                    membersSelected = membersSelected,
                    linksSelected = linksSelected,
                    filesSelected = filesSelected
                )

                if (membersSelected.value) {
                    MembersContent(
                        getGroupByIdData = getGroupByIdData,
                        groupViewModel = groupViewModel,
                        openRemoveUserFromGroupDialog = openRemoveUserFromGroupDialog
                    )
                }
                if (linksSelected.value) {
                    LinksContent(
                        context = context,
                        groupViewModel = groupViewModel,
                        getGroupByIdData = getGroupByIdData,
                        openRemoveLinkDialog = openRemoveLinkDialog
                    )
                }
                if (filesSelected.value) {
                    Box(
                        modifier = Modifier
                            .padding(20.dp)
                            .size(125.dp)
                            .clip(CircleShape)
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = Constants.CONTAINER_BASE_URL + getGroupByIdData.response?.groupImages?.get(
                                    0
                                )?.urlStorageReference,
                                builder = {
                                    crossfade(200)
                                    transformations(
                                        CircleCropTransformation()
                                    )
                                }
                            ),
                            contentDescription = "User image",
                            modifier = Modifier
                                .padding(10.dp)
                                .size(50.dp)
                        )
                    }
                }
            }
        }
        is APIRequestState.BadResponse -> {}
        is APIRequestState.EmptyList -> {}
        is APIRequestState.Idle -> {}
    }
}