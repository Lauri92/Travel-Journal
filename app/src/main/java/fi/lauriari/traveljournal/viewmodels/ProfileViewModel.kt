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
import fi.lauriari.traveljournal.AddGroupMutation
import fi.lauriari.traveljournal.GetGroupsByUserIdQuery
import fi.lauriari.traveljournal.LoginQuery
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
    var username: String = ""
    var userId: String = ""
    var imageUriState: MutableState<Uri?> = mutableStateOf(null)


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


    fun profilePictureUpload(
        context: Context,
        file: Upload
    ) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.profilePictureUpload(
                context = context,
                file = file
            ).collect { profilePictureUploadResponse ->
                Log.d("uploadTest", profilePictureUploadResponse?.data.toString())
            }
        }
    }
}