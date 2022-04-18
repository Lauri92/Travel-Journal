package fi.lauriari.traveljournal.util

sealed class APIRequestState<out T> {
    object Idle : APIRequestState<Nothing>()
    object Loading : APIRequestState<Nothing>()
    object EmptyList : APIRequestState<Nothing>()
    class BadResponse<T>(val error: String) : APIRequestState<T>()
    data class Success<T>(val response: T) : APIRequestState<T>()
}
