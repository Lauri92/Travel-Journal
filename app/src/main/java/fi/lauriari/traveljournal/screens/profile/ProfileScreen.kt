package fi.lauriari.traveljournal.screens.profile

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    navigateToLoginScreen: () -> Unit,
) {
    Scaffold(
        content = {
            ProfileScreenContent(
                navigateToLoginScreen = navigateToLoginScreen
            )
        }
    )
}