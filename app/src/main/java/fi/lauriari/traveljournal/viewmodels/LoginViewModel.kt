package fi.lauriari.traveljournal.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val usernameTextState: MutableState<String> = mutableStateOf("abc")
    val passwordTextState: MutableState<String> = mutableStateOf("")
    val registerUsernameTextState: MutableState<String> = mutableStateOf("")
    val registerPasswordTextState: MutableState<String> = mutableStateOf("")
    val passwordRetypeTextState: MutableState<String> = mutableStateOf("")

}