package fi.lauriari.traveljournal.screens.group

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.ui.theme.backGroundBlue
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.viewmodels.GroupViewModel


@Composable
fun GroupScreenContent(
    navigateToProfileScreen: () -> Unit,
    groupViewModel: GroupViewModel,
    getGroupByIdData: APIRequestState<GetGroupQuery.GetGroup?>
) {

    val membersSelected = remember { mutableStateOf(true) }
    val linksSelected = remember { mutableStateOf(false) }
    val filesSelected = remember { mutableStateOf(false) }

    when (getGroupByIdData) {
        is APIRequestState.Loading -> {
            Column(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(top = 100.dp),
                    text = "Loading group...",
                    fontSize = 25.sp,
                )
            }
        }
        is APIRequestState.Success -> {
            Column(
                modifier = Modifier
                    .background(color = backGroundBlue)
                    .fillMaxSize(),
            ) {
                GroupScreenContentHeader(
                    navigateToProfileScreen = navigateToProfileScreen,
                    getGroupByIdData = getGroupByIdData
                )

                AddMembersRow(
                    adminId = getGroupByIdData.response?.admin?.id,
                    groupViewModel = groupViewModel,
                    membersSelected = membersSelected,
                    linksSelected = linksSelected,
                )

                GroupItemsRow(
                    membersSelected = membersSelected,
                    linksSelected = linksSelected,
                    filesSelected = filesSelected
                )

                if (membersSelected.value) {
                    MembersContent(getGroupByIdData = getGroupByIdData)
                }
            }
        }
        is APIRequestState.BadResponse -> {}
        is APIRequestState.EmptyList -> {}
        is APIRequestState.Idle -> {}
    }
}

@Composable
fun GroupScreenContentHeader(
    navigateToProfileScreen: () -> Unit,
    getGroupByIdData: APIRequestState.Success<GetGroupQuery.GetGroup?>
) {
    Row(
    ) {
        IconButton(onClick = { navigateToProfileScreen() }) {
            Icon(
                modifier = Modifier.padding(10.dp),
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Admin indicator",
                tint = Color.Black
            )
        }
    }
    Row() {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .size(125.dp)
                .clip(CircleShape)
                .background(Color.DarkGray)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val groupnameStartingLetter =
                    getGroupByIdData.response?.name?.get(0).toString().uppercase()
                Text(
                    text = groupnameStartingLetter,
                    fontSize = 75.sp
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 15.dp, top = 10.dp, end = 10.dp)
        ) {
            Text(
                text = getGroupByIdData.response?.name!!,
                fontSize = 30.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = getGroupByIdData.response.description!!,
                fontSize = 20.sp,
            )
        }
    }
}

@Composable
fun AddMembersRow(
    groupViewModel: GroupViewModel,
    membersSelected: MutableState<Boolean>,
    linksSelected: MutableState<Boolean>,
    adminId: String?
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        when {
            membersSelected.value -> {
                val enabled = groupViewModel.userId == adminId
                OutlinedButton(
                    modifier = Modifier
                        .size(width = 200.dp, height = 60.dp),
                    shape = CircleShape,
                    onClick = { /* TODO: Open add members dialog */ },
                    enabled = enabled,
                ) {
                    Text(text = "Add a member", fontSize = 20.sp)
                }
            }
            linksSelected.value -> {
                OutlinedButton(
                    modifier = Modifier
                        .size(width = 200.dp, height = 60.dp),
                    shape = CircleShape,
                    onClick = { /* TODO: Open add members dialog */ }) {
                    Text(text = "Add a link", fontSize = 20.sp)
                }
            }
            else -> {
                OutlinedButton(
                    modifier = Modifier
                        .size(width = 200.dp, height = 60.dp),
                    shape = CircleShape,
                    onClick = { /* TODO: Open add members dialog */ }) {
                    Text(text = "Add a file", fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun GroupItemsRow(
    membersSelected: MutableState<Boolean>,
    linksSelected: MutableState<Boolean>,
    filesSelected: MutableState<Boolean>
) {
    var membersTextDecoration: TextDecoration = TextDecoration.None
    var linksTextDecoration: TextDecoration = TextDecoration.None
    var filesTextDecoration: TextDecoration = TextDecoration.None

    if (membersSelected.value) {
        membersTextDecoration = TextDecoration.Underline
    }
    if (linksSelected.value) {
        linksTextDecoration = TextDecoration.Underline
    }
    if (filesSelected.value) {
        filesTextDecoration = TextDecoration.Underline
    }

    Row(
        modifier = Modifier
            .border(width = 1.dp, color = Color.Gray)
            .padding(15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.clickable {
                membersSelected.value = true
                linksSelected.value = false
                filesSelected.value = false
            },
            text = "Members",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            style = TextStyle(textDecoration = membersTextDecoration)
        )
        Text(
            modifier = Modifier.clickable {
                membersSelected.value = false
                linksSelected.value = true
                filesSelected.value = false
            },
            text = "Links",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            style = TextStyle(textDecoration = linksTextDecoration)
        )
        Text(
            modifier = Modifier.clickable {
                membersSelected.value = false
                linksSelected.value = false
                filesSelected.value = true
            },
            text = "Files",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            style = TextStyle(textDecoration = filesTextDecoration)
        )
    }
}

@Composable
fun MembersContent(getGroupByIdData: APIRequestState.Success<GetGroupQuery.GetGroup?>) {
    data class Member(val id: String, val username: String)

    val list = getGroupByIdData.response?.members!!
    val admin = getGroupByIdData.response.admin
    val adminAndMembersList = mutableListOf<Member>()
    adminAndMembersList.add(Member(admin?.id!!, admin.username!!))
    list.forEach { member ->
        adminAndMembersList.add(Member(member?.id!!, member.username!!))
    }
    LazyColumn() {
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
                            member.username.get(0).toString().uppercase()

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