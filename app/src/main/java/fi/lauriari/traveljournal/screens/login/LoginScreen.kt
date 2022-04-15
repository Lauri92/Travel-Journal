package fi.lauriari.traveljournal.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.apollographql.apollo3.api.ApolloResponse
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


    Scaffold(
        content = {
            when (registerUserData) {
                is APIRequestState.Loading -> {
                    Toast.makeText(context, "Processing...", Toast.LENGTH_SHORT).show()
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