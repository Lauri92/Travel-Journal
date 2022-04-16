package fi.lauriari.traveljournal.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import fi.lauriari.traveljournal.data.Repository

class ProfileViewModel : ViewModel() {

    private val repository = Repository()

    val groupNameTextState: MutableState<String> = mutableStateOf("")
    val descriptionTextState: MutableState<String> = mutableStateOf("")



}