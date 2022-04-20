package fi.lauriari.traveljournal.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.lauriari.traveljournal.AddLinkMutation
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.GetGroupsByUserIdQuery
import fi.lauriari.traveljournal.data.GroupRepository
import fi.lauriari.traveljournal.util.APIRequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GroupViewModel : ViewModel() {


    var userId: String = ""
    var groupId: String = ""
    var urlTextState: MutableState<String> = mutableStateOf("")

    private val repository = GroupRepository()

    private var _getGroupByIdData =
        MutableStateFlow<APIRequestState<GetGroupQuery.GetGroup?>>(
            APIRequestState.Idle
        )
    val getGroupByIdData: StateFlow<APIRequestState<GetGroupQuery.GetGroup?>> =
        _getGroupByIdData

    fun getGroupById(context: Context) {
        _getGroupByIdData.value = APIRequestState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.getGroupById(
                context = context, groupId = groupId
            ).collect { getGroupById ->
                if (getGroupById?.data?.getGroup != null &&
                    !getGroupById.hasErrors()
                ) {
                    Log.d("singletest", getGroupById.data!!.getGroup.toString())
                    _getGroupByIdData.value =
                        APIRequestState.Success(getGroupById.data!!.getGroup)
                } else {
                    _getGroupByIdData.value =
                        APIRequestState.BadResponse("Failed to load groups")
                }
            }
        }
    }

    private var _addGroupData = MutableStateFlow<APIRequestState<AddLinkMutation.AddInfoLink?>>(
        APIRequestState.Idle
    )
    val addGroupData: StateFlow<APIRequestState<AddLinkMutation.AddInfoLink?>> =
        _addGroupData

    fun addLink(context: Context) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.addGroup(
                context = context,
                url = urlTextState.value,
                groupId = groupId
            ).collect { addLinkResponse ->
                if (addLinkResponse?.data?.addInfoLink != null &&
                    !addLinkResponse.hasErrors()
                ) {
                    _addGroupData.value =
                        APIRequestState.Success(addLinkResponse.data?.addInfoLink)
                } else {
                    _addGroupData.value =
                        APIRequestState.BadResponse("Failed to add link")
                }
            }
        }
    }


}