package fi.lauriari.traveljournal.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.lauriari.traveljournal.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val repository = Repository()

    val groupNameTextState: MutableState<String> = mutableStateOf("")
    val descriptionTextState: MutableState<String> = mutableStateOf("")


    fun addGroup(context: Context) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.addGroup(
                context = context,
                groupName = groupNameTextState.value,
                description = descriptionTextState.value
            ).collect { addGroupResponse ->
                groupNameTextState.value = ""
                descriptionTextState.value = ""
                Log.d("addgrouptry", addGroupResponse?.data.toString())
            }
        }
    }
}