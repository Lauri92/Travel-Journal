package fi.lauriari.traveljournal.navigation.destinations

import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fi.lauriari.traveljournal.screens.profile.ProfileScreen
import fi.lauriari.traveljournal.util.Constants
import fi.lauriari.traveljournal.util.User
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

fun NavGraphBuilder.profileComposable(
    profileViewModel: ProfileViewModel,
    navigateToLoginScreen: () -> Unit
) {
    composable(
        route = Constants.PROFILE_SCREEN
    ) { navBackStackEntry ->
        ProfileScreen(
            profileViewModel = profileViewModel,
            navigateToLoginScreen = navigateToLoginScreen
        )

    }
}