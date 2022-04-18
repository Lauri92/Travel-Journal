package fi.lauriari.traveljournal.navigation

import androidx.navigation.NavHostController
import fi.lauriari.traveljournal.util.Constants


class Screens(
    navController: NavHostController
) {
    val login: () -> Unit = {
        navController.navigate(route = "login")
    }

    val profile: () -> Unit = {
        navController.navigate(route = "profile")
    }

    val group: (String) -> Unit = { groupId ->
        navController.navigate(route = "group/$groupId")
    }

}