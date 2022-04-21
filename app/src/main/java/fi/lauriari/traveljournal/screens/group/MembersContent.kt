package fi.lauriari.traveljournal.screens.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.util.APIRequestState

@Composable
fun MembersContent(
    getGroupByIdData: APIRequestState.Success<GetGroupQuery.GetGroup?>
) {
    data class Member(val id: String, val username: String)

    val list = getGroupByIdData.response?.members!!
    val admin = getGroupByIdData.response.admin
    val adminAndMembersList = mutableListOf<Member>()
    adminAndMembersList.add(Member(admin?.id!!, admin.username!!))
    list.forEach { member ->
        adminAndMembersList.add(Member(member?.id!!, member.username!!))
    }
    LazyColumn {
        items(adminAndMembersList) { member ->
            Row {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val usernameStartingLetter =
                            member.username[0].toString().uppercase()

                        Text(
                            text = usernameStartingLetter,
                            fontSize = 25.sp
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(50.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(member.username)
                }
            }
        }
    }
}