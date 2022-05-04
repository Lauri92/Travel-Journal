package fi.lauriari.traveljournal.navigation.destinations

import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import fi.lauriari.traveljournal.data.models.UserMessage
import fi.lauriari.traveljournal.screens.group.GroupScreen
import fi.lauriari.traveljournal.util.Constants.CHAT_MESSAGE_EVENT
import fi.lauriari.traveljournal.util.Constants.GROUP_ARGUMENT_KEY
import fi.lauriari.traveljournal.util.Constants.GROUP_SCREEN
import fi.lauriari.traveljournal.util.SocketHandler
import fi.lauriari.traveljournal.viewmodels.GroupViewModel
import org.json.JSONException
import org.json.JSONObject

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
            SocketHandler.setSocket(groupId = groupId)
            SocketHandler.establishConnection()
            groupViewModel.getGroupById(
                context = context
            )
        }
        val socket = SocketHandler.getSocket()


        val message = remember { mutableStateOf(UserMessage("", "")) }

        socket?.on(CHAT_MESSAGE_EVENT) { args ->
            Log.d("messagetest", "Got a message: ${args[0]}")
            val data = args[0] as JSONObject
            try {
                message.value = UserMessage(data.getString("username"), data.getString("msg"))

            } catch (e: JSONException) {
                Log.d("messagetest", "Something went wrong actually. $e")
            }
        }


        val getGroupByIdData by groupViewModel.getGroupByIdData.collectAsState()

        GroupScreen(
            groupViewModel = groupViewModel,
            selectAvatarLauncher = selectAvatarLauncher,
            selectGroupImageLauncher = selectGroupImageLauncher,
            navigateToProfileScreen = navigateToProfileScreen,
            getGroupByIdData = getGroupByIdData,
            message = message,
            socket = socket
        )
    }
}