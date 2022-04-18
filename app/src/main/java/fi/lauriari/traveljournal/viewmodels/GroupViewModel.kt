package fi.lauriari.traveljournal.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    private val repository = GroupRepository()

    private var _getGroupByIdData =
        MutableStateFlow<APIRequestState<GetGroupQuery.GetGroup?>>(
            APIRequestState.Idle
        )
    val getGroupByIdData: StateFlow<APIRequestState<GetGroupQuery.GetGroup?>> =
        _getGroupByIdData

    fun getGroupById(context: Context, groupId: String) {
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


}