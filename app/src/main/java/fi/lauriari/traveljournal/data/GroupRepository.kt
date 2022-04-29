package fi.lauriari.traveljournal.data

import android.content.Context
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Upload
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
                    GetGroupQuery(groupId)
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun updateGroup(
        context: Context,
        groupId: String,
        name: String,
        description: String
    ): Flow<ApolloResponse<UpdateGroupMutation.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).mutation(
                    UpdateGroupMutation(groupId, name, description)
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }
    }

    suspend fun deleteGroup(
        context: Context,
        groupId: String
    ): Flow<ApolloResponse<DeleteGroupMutation.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).mutation(
                    DeleteGroupMutation(groupId)
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }
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

    suspend fun removeLink(
        context: Context,
        groupId: String,
        linkId: String
    ): Flow<ApolloResponse<RemoveInfoLinkMutation.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).mutation(
                    RemoveInfoLinkMutation(groupId, linkId)
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

    suspend fun removeUserFromGroup(
        context: Context,
        groupId: String,
        userId: String
    ): Flow<ApolloResponse<RemoveUserFromGroupMutation.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).mutation(
                    RemoveUserFromGroupMutation(groupId, userId)
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun userSelfLeaveGroup(
        context: Context,
        groupId: String
    ): Flow<ApolloResponse<UserSelfLeaveGroupMutation.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).mutation(
                    UserSelfLeaveGroupMutation(groupId)
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

    suspend fun groupAvatarUpload(
        context: Context,
        file: Upload,
        groupId: String
    ): Flow<ApolloResponse<GroupAvatarUploadMutation.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).mutation(
                    GroupAvatarUploadMutation(
                        file,
                        groupId
                    )
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun groupImageUpload(
        context: Context,
        file: Upload,
        groupId: String,
        title: String?
    ): Flow<ApolloResponse<GroupImageUploadMutation.Data>?> {
        return flow {
            val response = try {
                apolloClient(context).mutation(
                    GroupImageUploadMutation(
                        file,
                        groupId,
                        Optional.presentIfNotNull(title)
                    )
                ).execute()
            } catch (e: Exception) {
                null
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}