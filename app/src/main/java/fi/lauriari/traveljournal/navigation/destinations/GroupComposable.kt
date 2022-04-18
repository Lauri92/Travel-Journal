package fi.lauriari.traveljournal.navigation.destinations

import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import fi.lauriari.traveljournal.screens.group.GroupScreen
import fi.lauriari.traveljournal.util.Constants.GROUP_ARGUMENT_KEY
import fi.lauriari.traveljournal.util.Constants.GROUP_SCREEN
import fi.lauriari.traveljournal.viewmodels.GroupViewModel

fun NavGraphBuilder.groupComposable(
    navigateToProfileScreen: () -> Unit,
    groupViewModel: GroupViewModel
) {
    composable(
        route = GROUP_SCREEN,
        arguments = listOf(navArgument(GROUP_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        val context = LocalContext.current
        val groupId = navBackStackEntry.arguments!!.getString(GROUP_ARGUMENT_KEY)

        LaunchedEffect(key1 = groupId) {
            groupViewModel.getGroupById(
                context = context,
                groupId = groupId!!
            )
        }

        val getGroupByIdData by groupViewModel.getGroupByIdData.collectAsState()

        GroupScreen(
            groupViewModel = groupViewModel,
            navigateToProfileScreen = navigateToProfileScreen,
            getGroupByIdData = getGroupByIdData
        )


    }
}