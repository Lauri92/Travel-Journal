package fi.lauriari.traveljournal.data

import android.content.Context
import com.apollographql.apollo3.api.ApolloResponse
import fi.lauriari.traveljournal.AddLinkMutation
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.apolloClient
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

    suspend fun addGroup(
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

}