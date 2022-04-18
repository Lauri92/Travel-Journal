package fi.lauriari.traveljournal.screens.group

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import fi.lauriari.traveljournal.viewmodels.GroupViewModel

@Composable
fun GroupScreen(
    navigateToProfileScreen: () -> Unit,
    groupViewModel: GroupViewModel
) {


    Scaffold(
        content = {
            GroupScreenContent(
                groupViewModel = groupViewModel,
                navigateToProfileScreen = navigateToProfileScreen
            )
        }
    )
}