package fi.lauriari.traveljournal.screens.login

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollographql.apollo3.api.ApolloResponse
import fi.lauriari.traveljournal.LoginQuery
import fi.lauriari.traveljournal.RegisterUserMutation
import fi.lauriari.traveljournal.ui.theme.backGroundBlue
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.util.User
import fi.lauriari.traveljournal.viewmodels.LoginViewModel

@Composable
fun LoginScreenContent(
    loginViewModel: LoginViewModel,
    navigateToUserScreen: () -> Unit,
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
            .background(color = backGroundBlue)
            .padding(top = 100.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isLoginInputSelected) {
            LoginInputs(
                loginViewModel = loginViewModel,
                navigateToUserScreen = navigateToUserScreen,
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
    navigateToUserScreen: () -> Unit,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val loginUserData by loginViewModel.loginUserData.collectAsState()
    var isInputAllowed by remember { mutableStateOf(true) }

    when (loginUserData) {
        is APIRequestState.Loading -> {
            Toast.makeText(context, "Attempting to log in...", Toast.LENGTH_SHORT).show()
            isInputAllowed = false
        }
        is APIRequestState.Success -> {
            Toast.makeText(
                context,
                "Welcome ${(loginUserData as APIRequestState.Success<ApolloResponse<LoginQuery.Data>?>).response?.data?.login?.username}",
                Toast.LENGTH_LONG
            ).show()
            loginViewModel.setloginUserDataIdle()
            navigateToUserScreen()
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

    Text(
        text = "Login",
        color = Color.Black,
        fontSize = 30.sp
    )
    Spacer(modifier = Modifier.height(20.dp))
    LoginTextField(
        placeholderText = "Login Username",
        textState = usernameTextState,
        onTextChanged = onUsernameTextChanged,
        isInputAllowed = isInputAllowed,
        textVisibility = true
    )

    LoginTextField(
        placeholderText = "Login Password",
        textState = passwordTextState,
        onTextChanged = onPasswordTextChanged,
        isInputAllowed = isInputAllowed,
        textVisibility = false
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "No account? Register here.",
        style = TextStyle(textDecoration = TextDecoration.Underline),
        color = Color.Black,
        modifier = Modifier
            .clickable {
                selectRegisterInputs()
            },
    )
    OutlinedButton(
        enabled = isInputAllowed,
        modifier = Modifier
            .padding(16.dp)
            .size(width = 200.dp, height = 60.dp),
        onClick = {
            if (passwordTextState != "" && usernameTextState != "") {
                isInputAllowed = false
                onLoginPressed()
                focusManager.clearFocus()
            } else {
                Toast.makeText(context, "Fill both fields!", Toast.LENGTH_SHORT).show()
            }
        }
    ) {
        Text(
            text = "Login",
            fontSize = 20.sp
        )
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
    val focusManager = LocalFocusManager.current
    val registerUserData by loginViewModel.registerUserData.collectAsState()
    var isInputAllowed by remember { mutableStateOf(true) }

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
            selectLoginInputs()
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

    Text(
        text = "Register",
        color = Color.Black,
        fontSize = 30.sp
    )
    Spacer(modifier = Modifier.height(20.dp))
    LoginTextField(
        placeholderText = "Register Username",
        textState = registerUsernameTextState,
        onTextChanged = onRegisterUsernameTextChanged,
        isInputAllowed = isInputAllowed,
        textVisibility = true
    )

    LoginTextField(
        placeholderText = "Register Password",
        textState = registerPasswordTextState,
        onTextChanged = onRegisterPasswordTextChanged,
        isInputAllowed = isInputAllowed,
        textVisibility = false
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Already have an account?",
        style = TextStyle(textDecoration = TextDecoration.Underline),
        color = Color.Black,
        modifier = Modifier
            .clickable {
                selectLoginInputs()
            },
    )
    OutlinedButton(
        enabled = isInputAllowed,
        modifier = Modifier
            .padding(16.dp)
            .size(width = 200.dp, height = 60.dp),
        onClick = {
            if (registerPasswordTextState != "" && registerUsernameTextState != "") {
                isInputAllowed = false
                onRegisterPressed()
                focusManager.clearFocus()
            } else {
                Toast.makeText(context, "Fill both fields!", Toast.LENGTH_SHORT).show()
            }
        }
    ) {
        Text(
            text = "Register",
            fontSize = 20.sp
        )
    }
}