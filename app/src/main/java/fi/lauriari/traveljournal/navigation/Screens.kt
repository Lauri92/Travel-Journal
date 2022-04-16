package fi.lauriari.traveljournal.navigation

import androidx.navigation.NavHostController
import fi.lauriari.traveljournal.util.Constants


class Screens(
    navController: NavHostController
) {
    val login: () -> Unit = {
        navController.navigate(route = Constants.LOGIN_SCREEN)
    }

    val profile: () -> Unit = {
        navController.navigate(route = Constants.PROFILE_SCREEN)
    }

}