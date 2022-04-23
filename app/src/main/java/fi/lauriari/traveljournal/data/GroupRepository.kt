package fi.lauriari.traveljournal.data

import android.content.Context
import com.apollographql.apollo3.api.ApolloResponse
import fi.lauriari.traveljournal.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GroupRepository {

    suspend fun getGroupById(
        context: Context,
        groupId: String
    ): Flow<ApolloResponse<GetGroupQuery.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).query(
                    GetGroupQuery(getGroupId = groupId)
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addLink(
        context: Context,
        url: String,
        groupId: String
    ): Flow<ApolloResponse<AddLinkMutation.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).mutation(
                    AddLinkMutation(url, groupId)
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addUserToGroup(
        context: Context,
        groupId: String,
        userIdToBeAdded: String
    ): Flow<ApolloResponse<AddUserToGroupMutation.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).mutation(
                    AddUserToGroupMutation(groupId, userIdToBeAdded)
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    suspend fun searchUsers(
        context: Context,
        searchInput: String
    ): Flow<ApolloResponse<SearchUsersQuery.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).query(
                    SearchUsersQuery(searchInput)
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}