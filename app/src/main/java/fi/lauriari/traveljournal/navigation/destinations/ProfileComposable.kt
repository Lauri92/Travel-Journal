package fi.lauriari.traveljournal.navigation.destinations

import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fi.lauriari.traveljournal.util.Constants
import fi.lauriari.traveljournal.util.User

fun NavGraphBuilder.profileComposable(
) {
    composable(
        route = Constants.PROFILE_SCREEN
    ) { navBackStackEntry ->
        val context = LocalContext.current
        Text("Hello ${User.getUsername(context)}")
    }
}