package fi.lauriari.traveljournal.screens.group

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable

@Composable
fun GroupScreen(navigateToProfileScreen: () -> Unit) {


    Scaffold(
        content = {
            GroupScreenContent(
                navigateToProfileScreen = navigateToProfileScreen
            )
        }
    )
}