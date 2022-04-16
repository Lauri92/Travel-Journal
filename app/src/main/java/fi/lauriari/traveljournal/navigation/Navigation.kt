package fi.lauriari.traveljournal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import fi.lauriari.traveljournal.navigation.destinations.loginComposable
import fi.lauriari.traveljournal.navigation.destinations.profileComposable
import fi.lauriari.traveljournal.util.Constants.LOGIN_SCREEN
import fi.lauriari.traveljournal.util.Constants.PROFILE_SCREEN
import fi.lauriari.traveljournal.util.User
import fi.lauriari.traveljournal.viewmodels.LoginViewModel
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

@Composable
fun InitNavigation(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    profileViewModel: ProfileViewModel,
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }


    val startScreen = if (User.getToken(LocalContext.current) == null) {
        LOGIN_SCREEN
    } else {
        PROFILE_SCREEN
    }

    NavHost(
        navController = navController,
        startDestination = startScreen
    ) {
        loginComposable(
            loginViewModel = loginViewModel,
            navigateToUserScreen = screen.profile
        )
        profileComposable(
            profileViewModel = profileViewModel,
            navigateToLoginScreen = screen.login
        )
    }
}