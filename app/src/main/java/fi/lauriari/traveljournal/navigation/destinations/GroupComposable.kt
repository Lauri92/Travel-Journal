package fi.lauriari.traveljournal.navigation.destinations

import androidx.activity.result.ActivityResultLauncher
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
    groupViewModel: GroupViewModel,
    selectAvatarLauncher: ActivityResultLauncher<String>,
    selectGroupImageLauncher: ActivityResultLauncher<String>
) {
    composable(
        route = GROUP_SCREEN,
        arguments = listOf(navArgument(GROUP_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        val context = LocalContext.current
        val groupId = navBackStackEntry.arguments!!.getString(GROUP_ARGUMENT_KEY)
        groupViewModel.groupId = groupId!!

        LaunchedEffect(key1 = groupId) {
            groupViewModel.getGroupById(
                context = context
            )
        }

        val getGroupByIdData by groupViewModel.getGroupByIdData.collectAsState()

        GroupScreen(
            groupViewModel = groupViewModel,
            selectAvatarLauncher = selectAvatarLauncher,
            selectGroupImageLauncher = selectGroupImageLauncher,
            navigateToProfileScreen = navigateToProfileScreen,
            getGroupByIdData = getGroupByIdData
        )


    }
}