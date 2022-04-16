package fi.lauriari.traveljournal.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.apollographql.apollo3.api.ApolloResponse
import fi.lauriari.traveljournal.LoginQuery
import fi.lauriari.traveljournal.RegisterUserMutation
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.viewmodels.LoginViewModel

@Composable
fun LoginScreenContent(
    loginViewModel: LoginViewModel,
    usernameTextState: String,
    onUsernameTextChanged: (String) -> Unit,
    passwordTextState: String,
    onPasswordTextChanged: (String) -> Unit,
    registerUsernameTextState: String,
    onRegisterUsernameTextChanged: (String) -> Unit,
    registerPasswordTextState: String,
    onRegisterPasswordTextChanged: (String) -> Unit,
    onLoginPressed: () -> Unit,
    onRegisterPressed: () -> Unit,
) {

    var isLoginInputSelected by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .background(color = Color.LightGray)
            .padding(top = 100.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isLoginInputSelected) {
            LoginInputs(
                loginViewModel = loginViewModel,
                usernameTextState = usernameTextState,
                onUsernameTextChanged = onUsernameTextChanged,
                passwordTextState = passwordTextState,
                onPasswordTextChanged = onPasswordTextChanged,
                selectRegisterInputs = { isLoginInputSelected = false },
                onLoginPressed = onLoginPressed,
            )

        } else {
            RegisterInputs(
                loginViewModel = loginViewModel,
                registerUsernameTextState = registerUsernameTextState,
                onRegisterUsernameTextChanged = onRegisterUsernameTextChanged,
                registerPasswordTextState = registerPasswordTextState,
                onRegisterPasswordTextChanged = onRegisterPasswordTextChanged,
                selectLoginInputs = { isLoginInputSelected = true },
                onRegisterPressed = onRegisterPressed,
            )
        }
    }
}

@Composable
fun LoginInputs(
    usernameTextState: String,
    onUsernameTextChanged: (String) -> Unit,
    passwordTextState: String,
    onPasswordTextChanged: (String) -> Unit,
    selectRegisterInputs: () -> Unit,
    onLoginPressed: () -> Unit,
    loginViewModel: LoginViewModel,
) {
    val context = LocalContext.current
    val loginUserData by loginViewModel.loginUserData.collectAsState()
    var isInputAllowed by remember { mutableStateOf(true) }

    when (loginUserData) {
        is APIRequestState.Loading -> {
            Log.d("loggingtest", "logging in..")
            Toast.makeText(context, "Attempting to log in...", Toast.LENGTH_SHORT).show()
            isInputAllowed = false
        }
        is APIRequestState.Success -> {
            Log.d("loggingtest", "was success")
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
            loginViewModel.setloginUserDataIdle()
        }
        is APIRequestState.Idle -> {
            isInputAllowed = true
        }
    }

    LoginTextField(
        placeholderText = "Username",
        textState = usernameTextState,
        onTextChanged = onUsernameTextChanged,
        isInputAllowed = isInputAllowed
    )

    LoginTextField(
        placeholderText = "Password",
        textState = passwordTextState,
        onTextChanged = onPasswordTextChanged,
        isInputAllowed = isInputAllowed,
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "No account? Register here.",
        color = Color.DarkGray,
        modifier = Modifier
            .clickable {
                selectRegisterInputs()
            },
    )
    OutlinedButton(
        modifier = Modifier
            .padding(16.dp),
        onClick = { onLoginPressed() }
    ) {
        Text("Login")
    }
}

@Composable
fun RegisterInputs(
    registerUsernameTextState: String,
    onRegisterUsernameTextChanged: (String) -> Unit,
    registerPasswordTextState: String,
    onRegisterPasswordTextChanged: (String) -> Unit,
    selectLoginInputs: () -> Unit,
    onRegisterPressed: () -> Unit,
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current
    val registerUserData by loginViewModel.registerUserData.collectAsState()
    var isInputAllowed by remember { mutableStateOf(true) }

    when (registerUserData) {
        is APIRequestState.Loading -> {
            Log.d("loadingtest", "isloading..")
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
            isInputAllowed = true
        }
        is APIRequestState.Idle -> {
            isInputAllowed = true
        }
    }

    LoginTextField(
        placeholderText = "Username",
        textState = registerUsernameTextState,
        onTextChanged = onRegisterUsernameTextChanged,
        isInputAllowed = isInputAllowed
    )

    LoginTextField(
        placeholderText = "Password",
        textState = registerPasswordTextState,
        onTextChanged = onRegisterPasswordTextChanged,
        isInputAllowed = isInputAllowed,
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Already have an account?",
        color = Color.DarkGray,
        modifier = Modifier
            .clickable {
                selectLoginInputs()
            },
    )
    OutlinedButton(
        enabled = isInputAllowed,
        modifier = Modifier
            .padding(16.dp),
        onClick = { onRegisterPressed() }
    ) {
        Text("Register")
    }
}