package fi.lauriari.traveljournal.navigation.destinations

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fi.lauriari.traveljournal.screens.login.LoginScreen
import fi.lauriari.traveljournal.util.Constants.LOGIN_SCREEN
import fi.lauriari.traveljournal.util.User
import fi.lauriari.traveljournal.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.loginComposable(
    loginViewModel: LoginViewModel,
    navigateToUserScreen: () -> Unit
) {
    composable(
        route = LOGIN_SCREEN
    ) { navBackStackEntry ->
        val scope = rememberCoroutineScope()

        if (User.getToken(LocalContext.current) == null) {
            LoginScreen(
                loginViewModel = loginViewModel,
                navigateToUserScreen = navigateToUserScreen,
            )
        } else {
            scope.launch {
                navigateToUserScreen()
            }
        }
    }
}