package fi.lauriari.traveljournal.screens.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.data.models.Member
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.util.Constants
import fi.lauriari.traveljournal.viewmodels.GroupViewModel

@Composable
fun MembersContent(
    groupViewModel: GroupViewModel,
    getGroupByIdData: APIRequestState.Success<GetGroupQuery.GetGroup?>,
    openRemoveUserFromGroupDialog: MutableState<Boolean>
) {

    val list = getGroupByIdData.response?.members!!
    val admin = getGroupByIdData.response.admin
    val adminAndMembersList = mutableListOf<Member>()
    adminAndMembersList.add(Member(admin?.id!!, admin.username!!, admin.profileImageUrl))
    list.forEach { member ->
        adminAndMembersList.add(Member(member?.id!!, member.username!!, member.profileImageUrl))
    }
    LazyColumn {
        items(adminAndMembersList) { member ->
            Row {
                if (member.profileImageUrl == null) {
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
                } else {
                    Image(
                        painter = rememberImagePainter(
                            data = Constants.CONTAINER_BASE_URL + member.profileImageUrl,
                            builder = {
                                crossfade(200)
                                transformations(
                                    CircleCropTransformation()
                                )
                            }
                        ),
                        contentDescription = "User image",
                        modifier = Modifier
                            .padding(10.dp)
                            .size(50.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .size(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var expanded by remember { mutableStateOf(false) }

                    Text(member.username)

                    if (member.id != admin.id && groupViewModel.userId == admin.id) {
                        IconButton(
                            onClick = { expanded = true }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Remove from group button",
                                tint = Color.Black
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        groupViewModel.pressedUser = member.id
                                        expanded = false
                                        openRemoveUserFromGroupDialog.value = true
                                    }
                                ) {
                                    Text(
                                        text = "Remove user",
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}