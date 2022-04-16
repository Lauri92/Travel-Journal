package fi.lauriari.traveljournal.screens.login

import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.apollographql.apollo3.api.ApolloResponse
import fi.lauriari.traveljournal.LoginQuery
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

    Scaffold(
        content = {
            LoginScreenContent(
                loginViewModel = loginViewModel,
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
            )
        }
    )
}