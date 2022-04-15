package fi.lauriari.traveljournal.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.apollographql.apollo3.api.ApolloResponse
import fi.lauriari.traveljournal.LoginQuery
import fi.lauriari.traveljournal.RegisterUserMutation
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current
    val usernameTextState: String by loginViewModel.usernameTextState
    val passwordTextState: String by loginViewModel.passwordTextState
    val registerUsernameTextState: String by loginViewModel.registerUsernameTextState
    val registerPasswordTextState: String by loginViewModel.registerPasswordTextState

    val registerUserData by loginViewModel.registerUserData.collectAsState()
    val loginUserData by loginViewModel.loginUserData.collectAsState()

    var isInputAllowed by remember { mutableStateOf(true) }


    Scaffold(
        content = {
            when (registerUserData) {
                is APIRequestState.Loading -> {
                    Toast.makeText(context, "Processing...", Toast.LENGTH_SHORT).show()
                    isInputAllowed = false
                }
                is APIRequestState.Success -> {
                    Toast.makeText(
                        context,
                        "Registered user with username " +
                                "${(registerUserData as APIRequestState.Success<ApolloResponse<RegisterUserMutation.Data>?>).response?.data?.registerUser?.username}",
                        Toast.LENGTH_LONG
                    ).show()
                    loginViewModel.setRegisterDataIdle()
                }
                is APIRequestState.BadResponse -> {
                    Toast.makeText(
                        context,
                        "Failed to register user with username $registerUsernameTextState",
                        Toast.LENGTH_LONG
                    ).show()
                    loginViewModel.setRegisterDataIdle()
                }
                is APIRequestState.Idle -> {
                    isInputAllowed = true
                }
            }
            when (loginUserData) {
                is APIRequestState.Loading -> {
                    Toast.makeText(context, "Processing...", Toast.LENGTH_SHORT).show()
                    isInputAllowed = false
                }
                is APIRequestState.Success -> {
                    Toast.makeText(
                        context,
                        "Welcome ${(loginUserData as APIRequestState.Success<ApolloResponse<LoginQuery.Data>?>).response?.data?.login?.username}",
                        Toast.LENGTH_LONG
                    ).show()
                    loginViewModel.setloginUserDataIdle()
                }
                is APIRequestState.BadResponse -> {
                    Toast.makeText(
                        context,
                        "Failed to login",
                        Toast.LENGTH_LONG
                    ).show()
                    loginViewModel.setRegisterDataIdle()
                }
                is APIRequestState.Idle -> {
                    isInputAllowed = true
                }
            }


            LoginScreenContent(
                usernameTextState = usernameTextState,
                onUsernameTextChanged = { newUsernameText ->
                    loginViewModel.usernameTextState.value = newUsernameText
                },
                passwordTextState = passwordTextState,
                onPasswordTextChanged = { newPasswordText ->
                    loginViewModel.passwordTextState.value = newPasswordText
                },
                registerUsernameTextState = registerUsernameTextState,
                onRegisterUsernameTextChanged = { newRegisterUsernameText ->
                    loginViewModel.registerUsernameTextState.value = newRegisterUsernameText
                },
                registerPasswordTextState = registerPasswordTextState,
                onRegisterPasswordTextChanged = { newRegisterPasswordText ->
                    loginViewModel.registerPasswordTextState.value = newRegisterPasswordText
                },
                onLoginPressed = {
                    loginViewModel.loginUser(context)
                },
                onRegisterPressed = {
                    loginViewModel.registerUser(context)
                },
                isInputAllowed = isInputAllowed
            )
        }
    )
}