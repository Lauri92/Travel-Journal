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

    val usernameTextState: MutableState<String> = mutableStateOf("")
    val passwordTextState: MutableState<String> = mutableStateOf("")
    val registerUsernameTextState: MutableState<String> = mutableStateOf("")
    val registerPasswordTextState: MutableState<String> = mutableStateOf("")


    private var _registerUserData =
        MutableStateFlow<APIRequestState<ApolloResponse<RegisterUserMutation.Data>?>>(
            APIRequestState.Idle
        )
    val registerUserData: StateFlow<APIRequestState<ApolloResponse<RegisterUserMutation.Data>?>> =
        _registerUserData

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
                if (registerResponse?.data?.registerUser != null) {
                    _registerUserData.value = APIRequestState.Success(registerResponse)
                } else {
                    _registerUserData.value = APIRequestState.BadResponse
                }
            }
        }
    }


    private var _loginUserData =
        MutableStateFlow<APIRequestState<ApolloResponse<LoginQuery.Data>?>>(
            APIRequestState.Idle
        )
    val loginUserData: StateFlow<APIRequestState<ApolloResponse<LoginQuery.Data>?>> =
        _loginUserData

    fun setloginUserDataIdle() {
        _loginUserData.value = APIRequestState.Idle
    }

    fun loginUser(context: Context) {
        _loginUserData.value = APIRequestState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.loginUser(
                context = context,
                username = usernameTextState.value,
                password = passwordTextState.value
            ).collect { loginResponse ->
                Log.d("Logintry", loginResponse?.data.toString())
                if (loginResponse?.data?.login?.token != null) {
                    _loginUserData.value = APIRequestState.Success(loginResponse)
                    User.setToken(context, loginResponse.data!!.login!!.token!!)
                } else {
                    _loginUserData.value = APIRequestState.BadResponse
                }
            }
        }
    }

}