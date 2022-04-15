package fi.lauriari.traveljournal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import fi.lauriari.traveljournal.navigation.destinations.loginComposable
import fi.lauriari.traveljournal.util.Constants.LOGIN_SCREEN

@Composable
fun InitNavigation(
    navController: NavHostController,
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = LOGIN_SCREEN
    ) {
        loginComposable()
    }
}