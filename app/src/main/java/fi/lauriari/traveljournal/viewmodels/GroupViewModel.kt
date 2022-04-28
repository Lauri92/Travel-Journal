package fi.lauriari.traveljournal.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.lauriari.traveljournal.*
import fi.lauriari.traveljournal.data.GroupRepository
import fi.lauriari.traveljournal.util.APIRequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GroupViewModel : ViewModel() {

    private val repository = GroupRepository()

    var avatarUriState: MutableState<Uri?> = mutableStateOf(null)
    var userId: String = ""
    var groupId: String = ""
    var pressedLink: String = ""
    var pressedUser: String = ""
    var groupMembers: List<GetGroupQuery.Member?>? = emptyList()
    var urlTextState: MutableState<String> = mutableStateOf("")
    val searchInputState: MutableState<String> = mutableStateOf("")
    val nameUpdateTextState: MutableState<String> = mutableStateOf("")
    val descriptionUpdateTextState: MutableState<String> = mutableStateOf("")


    private var _getGroupByIdData =
        MutableStateFlow<APIRequestState<GetGroupQuery.GetGroup?>>(
            APIRequestState.Idle
        )
    val getGroupByIdData: StateFlow<APIRequestState<GetGroupQuery.GetGroup?>> =
        _getGroupByIdData

    fun getGroupById(context: Context) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.getGroupById(
                context = context,
                groupId = groupId
            ).collect { getGroupById ->
                if (getGroupById?.data?.getGroup != null &&
                    !getGroupById.hasErrors()
                ) {
                    Log.d("singletest", getGroupById.data!!.getGroup.toString())
                    nameUpdateTextState.value = getGroupById.data!!.getGroup?.name!!
                    descriptionUpdateTextState.value = getGroupById.data!!.getGroup?.description!!

                    _getGroupByIdData.value =
                        APIRequestState.Success(getGroupById.data!!.getGroup)
                    groupMembers = getGroupById.data!!.getGroup?.members
                } else {
                    _getGroupByIdData.value =
                        APIRequestState.BadResponse("Failed to load groups")
                }
            }
        }
    }

    private var _updateGroupData =
        MutableStateFlow<APIRequestState<UpdateGroupMutation.UpdateGroup?>>(
            APIRequestState.Idle
        )
    val updateGroupData: StateFlow<APIRequestState<UpdateGroupMutation.UpdateGroup?>> =
        _updateGroupData

    fun setUpdateGroupDataIdle() {
        _updateGroupData.value = APIRequestState.Idle
    }

    fun updateGroup(context: Context) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.updateGroup(
                context = context,
                groupId = groupId,
                name = nameUpdateTextState.value,
                description = descriptionUpdateTextState.value
            ).collect { updateGroupResponse ->
                if (updateGroupResponse?.data?.updateGroup != null &&
                    !updateGroupResponse.hasErrors()
                ) {
                    _updateGroupData.value =
                        APIRequestState.Success(updateGroupResponse.data!!.updateGroup)
                } else {
                    _updateGroupData.value = APIRequestState.BadResponse("Failed to update group")
                }
            }
        }
    }

    private var _deleteGroupData = MutableStateFlow<APIRequestState<String?>>(APIRequestState.Idle)

    val deleteGroupData: StateFlow<APIRequestState<String?>> = _deleteGroupData

    fun setDeleteUserDataIdle() {
        _deleteGroupData.value = APIRequestState.Idle
    }

    fun deleteGroup(context: Context) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.deleteGroup(
                context = context,
                groupId = groupId
            ).collect { deleteGroupResponse ->
                if (deleteGroupResponse?.data?.deleteGroup != null &&
                    !deleteGroupResponse.hasErrors()
                ) {
                    _deleteGroupData.value =
                        APIRequestState.Success(deleteGroupResponse.data!!.deleteGroup)
                } else {
                    _deleteGroupData.value =
                        APIRequestState.BadResponse("Failed to delete group.")
                }
            }
        }
    }

    private var _addLinkData = MutableStateFlow<APIRequestState<AddLinkMutation.AddInfoLink?>>(
        APIRequestState.Idle
    )
    val addLinkData: StateFlow<APIRequestState<AddLinkMutation.AddInfoLink?>> =
        _addLinkData

    fun setAddLinkDataIdle() {
        _addLinkData.value = APIRequestState.Idle
    }

    fun addLink(context: Context) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.addLink(
                context = context,
                url = urlTextState.value,
                groupId = groupId
            ).collect { addLinkResponse ->
                if (addLinkResponse?.data?.addInfoLink != null &&
                    !addLinkResponse.hasErrors()
                ) {
                    _addLinkData.value =
                        APIRequestState.Success(addLinkResponse.data?.addInfoLink)
                } else {
                    _addLinkData.value =
                        APIRequestState.BadResponse("Failed to add link")
                }
            }
        }
    }

    private var _removeLinkData = MutableStateFlow<APIRequestState<String?>>(
        APIRequestState.Idle
    )
    val removeLinkData: StateFlow<APIRequestState<String?>> = _removeLinkData

    fun setRemoveLinkDataIdle() {
        _removeLinkData.value = APIRequestState.Idle
    }

    fun removeLink(context: Context) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.removeLink(
                context = context,
                linkId = pressedLink,
                groupId = groupId
            ).collect { removeLinkRespose ->
                if (removeLinkRespose?.data?.removeInfoLink != null &&
                    !removeLinkRespose.hasErrors()
                ) {
                    _removeLinkData.value =
                        APIRequestState.Success(removeLinkRespose.data!!.removeInfoLink)
                } else {
                    _removeLinkData.value = APIRequestState.BadResponse("Failed to remove link")
                }
            }
        }
    }


    private var _addUserToGroupData =
        MutableStateFlow<APIRequestState<AddUserToGroupMutation.AddUserToGroup?>>(APIRequestState.Idle)

    val addUserToGroupData: StateFlow<APIRequestState<AddUserToGroupMutation.AddUserToGroup?>> =
        _addUserToGroupData

    fun setAddUserToGroupDataIdle() {
        _addUserToGroupData.value = APIRequestState.Idle
    }

    fun addUserToGroup(
        context: Context,
        userIdToBeAdded: String
    ) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.addUserToGroup(
                context = context,
                groupId = groupId,
                userIdToBeAdded = userIdToBeAdded
            ).collect { addUserToGroupResponse ->
                if (addUserToGroupResponse?.data?.addUserToGroup != null && !addUserToGroupResponse.hasErrors()) {
                    _addUserToGroupData.value =
                        APIRequestState.Success(addUserToGroupResponse.data!!.addUserToGroup)
                } else {
                    _addUserToGroupData.value =
                        APIRequestState.BadResponse("Failed to add user to group.")
                }
            }
        }
    }

    private var _removeUserFromGroupData =
        MutableStateFlow<APIRequestState<String?>>(APIRequestState.Idle)

    val removeUserFromGroupData: StateFlow<APIRequestState<String?>> =
        _removeUserFromGroupData

    fun setRemoveUserFromGroupDataIdle() {
        _removeUserFromGroupData.value = APIRequestState.Idle
    }

    fun removeUserFromGroup(
        context: Context,
    ) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.removeUserFromGroup(
                context = context,
                groupId = groupId,
                userId = pressedUser
            ).collect { removeUserFromGroupResponse ->
                if (removeUserFromGroupResponse?.data?.removeUserFromGroup != null &&
                    !removeUserFromGroupResponse.hasErrors()
                ) {
                    _removeUserFromGroupData.value =
                        APIRequestState.Success(removeUserFromGroupResponse.data!!.removeUserFromGroup)
                } else {
                    _removeUserFromGroupData.value =
                        APIRequestState.BadResponse("Failed to remove user from group")
                }
            }
        }
    }


    private var _userSelfLeaveGroupData =
        MutableStateFlow<APIRequestState<String?>>(APIRequestState.Idle)

    val userSelfLeaveGroupData: StateFlow<APIRequestState<String?>> =
        _userSelfLeaveGroupData

    fun setUserSelfLeaveGroupDataIdle() {
        _userSelfLeaveGroupData.value = APIRequestState.Idle
    }

    fun userSelfLeaveGroup(
        context: Context
    ) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.userSelfLeaveGroup(
                context = context,
                groupId = groupId
            ).collect { userSelfLeaveGroupResponse ->
                if (userSelfLeaveGroupResponse?.data?.userSelfLeaveGroup != null &&
                    !userSelfLeaveGroupResponse.hasErrors()
                ) {
                    _userSelfLeaveGroupData.value =
                        APIRequestState.Success(userSelfLeaveGroupResponse.data!!.userSelfLeaveGroup)
                } else {
                    _userSelfLeaveGroupData.value =
                        APIRequestState.BadResponse("Failed to leave group")
                }
            }
        }
    }


    private var _searchUsersData =
        MutableStateFlow<APIRequestState<List<SearchUsersQuery.SearchUser?>?>>(APIRequestState.Idle)
    val searchUsersData: StateFlow<APIRequestState<List<SearchUsersQuery.SearchUser?>?>> =
        _searchUsersData

    fun searchUsers(context: Context) {
        _searchUsersData.value = APIRequestState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.searchUsers(
                context = context,
                searchInput = searchInputState.value
            ).collect { searchUsersResponse ->
                Log.d(
                    "composetrace",
                    "searchUsersResponse: ${searchUsersResponse?.data?.searchUsers}"
                )
                if (searchUsersResponse?.data?.searchUsers != null &&
                    !searchUsersResponse.hasErrors()
                ) {
                    _searchUsersData.value =
                        APIRequestState.Success(searchUsersResponse.data!!.searchUsers)
                } else {
                    _searchUsersData.value = APIRequestState.BadResponse("Failed to fetch users.")
                }
            }
        }
    }
}