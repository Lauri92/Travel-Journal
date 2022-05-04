package fi.lauriari.traveljournal.data.models

data class UserMessage(
    val messageId: String,
    val username: String,
    val message: String,
    val userProfileImageUrl: String?,
)
