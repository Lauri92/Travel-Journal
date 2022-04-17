package fi.lauriari.traveljournal.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import fi.lauriari.traveljournal.LoginQuery
import fi.lauriari.traveljournal.RegisterUserMutation
import fi.lauriari.traveljournal.data.Repository
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.util.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = Repository()

    val usernameTextState: MutableState<String> = mutableStateOf("test")
    val passwordTextState: MutableState<String> = mutableStateOf("test")
    val registerUsernameTextState: MutableState<String> = mutableStateOf("")
    val registerPasswordTextState: MutableState<String> = mutableStateOf("")


    private var _registerUserData =
        MutableStateFlow<APIRequestState<RegisterUserMutation.Data?>>(APIRequestState.Idle)

    val registerUserData: StateFlow<APIRequestState<RegisterUserMutation.Data?>> = _registerUserData

    fun setRegisterDataIdle() {
        _registerUserData.value = APIRequestState.Idle
    }

    fun registerUser(context: Context) {
        _registerUserData.value = APIRequestState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.registerUser(
                context = context,
                username = registerUsernameTextState.value,
                password = registerPasswordTextState.value
            ).collect { registerResponse ->
                if (registerResponse?.data?.registerUser != null && !registerResponse.hasErrors()) {
                    _registerUserData.value = APIRequestState.Success(registerResponse.data)
                    loginUser(
                        context,
                        username = registerUsernameTextState.value,
                        password = registerPasswordTextState.value
                    )
                } else {
                    val errorMessage = registerResponse?.errors!![0].message
                    _registerUserData.value = APIRequestState.BadResponse(errorMessage)
                }
            }
        }
    }


    private var _loginUserData =
        MutableStateFlow<APIRequestState<LoginQuery.Data?>>(APIRequestState.Idle)

    val loginUserData: StateFlow<APIRequestState<LoginQuery.Data?>> = _loginUserData

    fun setloginUserDataIdle() {
        _loginUserData.value = APIRequestState.Idle
    }

    fun loginUser(
        context: Context,
        username: String = usernameTextState.value,
        password: String = passwordTextState.value
    ) {
        _loginUserData.value = APIRequestState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.loginUser(
                context = context,
                username = username,
                password = password
            ).collect { loginResponse ->
                if (loginResponse?.data?.login?.token != null && !loginResponse.hasErrors()) {
                    User.setToken(context, loginResponse.data!!.login!!.token!!)
                    User.setUsername(context, loginResponse.data!!.login!!.username!!)
                    _loginUserData.value = APIRequestState.Success(loginResponse.data)
                    usernameTextState.value = ""
                    passwordTextState.value = ""
                    registerUsernameTextState.value = ""
                    registerPasswordTextState.value = ""
                } else {
                    val errorMessage = loginResponse?.errors!![0].message
                    _loginUserData.value = APIRequestState.BadResponse(errorMessage)
                }
            }
        }
    }
}