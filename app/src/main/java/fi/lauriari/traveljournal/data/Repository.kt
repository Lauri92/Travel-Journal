package fi.lauriari.traveljournal.data

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import com.apollographql.apollo3.api.ApolloResponse
import fi.lauriari.traveljournal.RegisterUserMutation
import fi.lauriari.traveljournal.apolloClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository {

    suspend fun registerUser(
        context: Context,
        username: String,
        password: String
    ): Flow<ApolloResponse<RegisterUserMutation.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).mutation(
                    RegisterUserMutation(
                        username = username,
                        password = password
                    )
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}