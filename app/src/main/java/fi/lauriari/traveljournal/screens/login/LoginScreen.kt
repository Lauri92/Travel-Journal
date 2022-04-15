package fi.lauriari.traveljournal.screens.login

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import fi.lauriari.traveljournal.viewmodels.LoginViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {

    val usernameTextState: String by loginViewModel.usernameTextState
    val passwordTextState: String by loginViewModel.passwordTextState
    val registerUsernameTextState: String by loginViewModel.registerUsernameTextState
    val registerPasswordTextState: String by loginViewModel.registerPasswordTextState
    val passwordRetypeTextState: String by loginViewModel.passwordRetypeTextState

    Scaffold(
        content = {
            val context = LocalContext.current
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
                passwordRetypeTextState = passwordRetypeTextState,
                onPasswordRetypeTextChanged = { newPasswordRetypeText ->
                    loginViewModel.passwordRetypeTextState.value = newPasswordRetypeText
                },
                onLoginPressed = {
                    Log.d(
                        "logintest",
                        "Username: ${loginViewModel.usernameTextState.value}" +
                                " Password: ${loginViewModel.passwordTextState.value}"
                    )
                },
                onRegisterPressed = {
                    loginViewModel.registerUser(context)
                }
            )
        }
    )
}