package fi.lauriari.traveljournal.data

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.ApolloResponse
import fi.lauriari.traveljournal.*
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

    suspend fun loginUser(
        context: Context,
        username: String,
        password: String
    ): Flow<ApolloResponse<LoginQuery.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).query(
                    LoginQuery(
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


    suspend fun addGroup(
        context: Context,
        groupName: String,
        description: String
    ): Flow<ApolloResponse<AddGroupMutation.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).mutation(
                    AddGroupMutation(
                        name = groupName,
                        description = description
                    )
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getGroupsByUserId(
        context: Context
    ): Flow<ApolloResponse<GetGroupsByUserIdQuery.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).query(
                    GetGroupsByUserIdQuery()
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }


}