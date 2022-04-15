package fi.lauriari.traveljournal.navigation

import androidx.navigation.NavHostController

class Screens(
    navController: NavHostController
) {
    val login: () -> Unit = {
        navController.navigate(route = "login")
    }
}