package fi.lauriari.traveljournal.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fi.lauriari.traveljournal.screens.login.LoginScreen
import fi.lauriari.traveljournal.util.Constants.LOGIN_SCREEN

fun NavGraphBuilder.loginComposable(
) {
    composable(
        route = LOGIN_SCREEN
    ) { navBackStackEntry ->
        LoginScreen()
    }
}