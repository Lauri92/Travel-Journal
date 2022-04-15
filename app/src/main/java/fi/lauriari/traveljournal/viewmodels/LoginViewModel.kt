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

class LoginViewModel : ViewModel() {

    private val repository = Repository()

    val usernameTextState: MutableState<String> = mutableStateOf("abc")
    val passwordTextState: MutableState<String> = mutableStateOf("")
    val registerUsernameTextState: MutableState<String> = mutableStateOf("")
    val registerPasswordTextState: MutableState<String> = mutableStateOf("")
    val passwordRetypeTextState: MutableState<String> = mutableStateOf("")

    fun registerUser(context: Context) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.registerUser(
                context = context,
                username = registerUsernameTextState.value,
                password = registerPasswordTextState.value
            ).collect { registerResponse ->
                Log.d("Registertry", registerResponse?.data.toString())
            }
        }
    }

}