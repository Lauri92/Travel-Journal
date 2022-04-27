package fi.lauriari.traveljournal.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Upload
import fi.lauriari.traveljournal.*
import fi.lauriari.traveljournal.data.Repository
import fi.lauriari.traveljournal.util.APIRequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val repository = Repository()

    val groupNameTextState: MutableState<String> = mutableStateOf("")
    val descriptionTextState: MutableState<String> = mutableStateOf("")
    var username: String = " "
    var userId: String = ""
    var userImage: String? = null
    var imageUriState: MutableState<Uri?> = mutableStateOf(null)


    private var _getActiveUserData =
        MutableStateFlow<APIRequestState<GetActiveUserQuery.GetActiveUser?>>(
            APIRequestState.Idle
        )
    val getActiveUserData: StateFlow<APIRequestState<GetActiveUserQuery.GetActiveUser?>> =
        _getActiveUserData

    fun setGetActiveUserDataIdle() {
        _getActiveUserData.value = APIRequestState.Idle
    }

    fun getActiveUser(context: Context) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.getActiveUser(context = context).collect { getActiveUserResponse ->
                if (getActiveUserResponse?.data?.getActiveUser != null &&
                    !getActiveUserResponse.hasErrors()
                ) {
                    Log.d("composetrace", getActiveUserResponse.data!!.getActiveUser.toString())
                    userId = getActiveUserResponse.data!!.getActiveUser!!.id!!
                    username = getActiveUserResponse.data!!.getActiveUser?.username!!
                    userImage = getActiveUserResponse.data!!.getActiveUser?.profileImageUrl
                    _getActiveUserData.value =
                        APIRequestState.Success(getActiveUserResponse.data!!.getActiveUser)
                } else {
                    _getActiveUserData.value =
                        APIRequestState.BadResponse("Failed to fetch user data!")
                }
            }
        }
    }


    private var _addGroupData =
        MutableStateFlow<APIRequestState<AddGroupMutation.AddGroup?>>(
            APIRequestState.Idle
        )
    val addGroupData: StateFlow<APIRequestState<AddGroupMutation.AddGroup?>> =
        _addGroupData

    fun setAddGroupDataIdle() {
        _addGroupData.value = APIRequestState.Idle
    }

    fun addGroup(context: Context) {
        _addGroupData.value = APIRequestState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.addGroup(
                context = context,
                groupName = groupNameTextState.value,
                description = descriptionTextState.value
            ).collect { addGroupResponse ->
                if (addGroupResponse?.data?.addGroup != null && !addGroupResponse.hasErrors()) {
                    groupNameTextState.value = ""
                    descriptionTextState.value = ""
                    _addGroupData.value = APIRequestState.Success(addGroupResponse.data!!.addGroup)
                } else {
                    val errorMessage = addGroupResponse!!.errors!![0].message
                    _addGroupData.value = APIRequestState.BadResponse(errorMessage)
                }
            }
        }
    }

    private var _getGroupsByUserIdData =
        MutableStateFlow<APIRequestState<List<GetGroupsByUserIdQuery.GetGroupsByUserId?>?>>(
            APIRequestState.Idle
        )
    val getGroupsByUserIdData: StateFlow<APIRequestState<List<GetGroupsByUserIdQuery.GetGroupsByUserId?>?>> =
        _getGroupsByUserIdData

    fun setGetGroupsByUserIdDataIdle() {
        _getGroupsByUserIdData.value = APIRequestState.Idle
    }

    fun getGroupsByUserId(context: Context) {
        _getGroupsByUserIdData.value = APIRequestState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.getGroupsByUserId(context = context)
                .collect { getGroupsByUserIdResponse ->
                    if (getGroupsByUserIdResponse?.data?.getGroupsByUserId != null &&
                        !getGroupsByUserIdResponse.hasErrors()
                    ) {
                        if (getGroupsByUserIdResponse.data!!.getGroupsByUserId!!.isEmpty()) {
                            _getGroupsByUserIdData.value = APIRequestState.EmptyList
                        } else {
                            _getGroupsByUserIdData.value =
                                APIRequestState.Success(getGroupsByUserIdResponse.data!!.getGroupsByUserId)
                        }
                    } else {
                        _getGroupsByUserIdData.value =
                            APIRequestState.BadResponse("Failed to load groups")
                    }
                }
        }
    }

    private var _profilePictureUploadData =
        MutableStateFlow<APIRequestState<ProfilePictureUploadMutation.ProfilePictureUpload?>>(
            APIRequestState.Idle
        )
    val profilePictureUploadData:
            StateFlow<APIRequestState<ProfilePictureUploadMutation.ProfilePictureUpload?>> =
        _profilePictureUploadData

    fun setProfilePictureUploadDataIdle() {
        _profilePictureUploadData.value = APIRequestState.Idle
    }


    fun profilePictureUpload(
        context: Context,
        file: Upload
    ) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.profilePictureUpload(
                context = context,
                file = file
            ).collect { profilePictureUploadResponse ->
                if (profilePictureUploadResponse?.data?.profilePictureUpload != null &&
                    !profilePictureUploadResponse.hasErrors()
                ) {
                    _profilePictureUploadData.value =
                        APIRequestState.Success(profilePictureUploadResponse.data?.profilePictureUpload)
                } else {
                    _profilePictureUploadData.value =
                        APIRequestState.BadResponse("Failed to upload image!")
                }
            }
        }
    }
}