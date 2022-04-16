package fi.lauriari.traveljournal.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fi.lauriari.traveljournal.screens.login.LoginScreen
import fi.lauriari.traveljournal.util.Constants.LOGIN_SCREEN
import fi.lauriari.traveljournal.viewmodels.LoginViewModel

fun NavGraphBuilder.loginComposable(
    loginViewModel: LoginViewModel,
    navigateToUserScreen: () -> Unit
) {
    composable(
        route = LOGIN_SCREEN
    ) { navBackStackEntry ->
        LoginScreen(
            loginViewModel = loginViewModel,
            navigateToUserScreen = navigateToUserScreen,
        )
    }
}