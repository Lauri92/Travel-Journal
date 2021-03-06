package fi.lauriari.traveljournal.util

object Constants {

    // Server url
    const val SERVER_URL = "https://sssf-travel-journal.azurewebsites.net/graphql"

    // Server base URL
    const val SERVER_BASE_URL = "https://sssf-travel-journal.azurewebsites.net/"

    // Container url
    const val CONTAINER_BASE_URL = "https://traveljournal.blob.core.windows.net/"

    // Shared prefs keys
    const val KEY_TOKEN = "TOKEN"
    const val KEY_USERNAME = "USERNAME"
    const val KEY_USER_ID = "USER_ID"

    // Destinations
    const val LOGIN_SCREEN = "login"
    const val PROFILE_SCREEN = "profile"
    const val GROUP_SCREEN = "group/{groupId}"

    // Argument keys
    const val GROUP_ARGUMENT_KEY = "groupId"

    // Socket events
    const val CHAT_MESSAGE_EVENT = "chat message"

}