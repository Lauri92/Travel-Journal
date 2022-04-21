package fi.lauriari.traveljournal.screens.group

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import fi.lauriari.traveljournal.SearchUsersQuery
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.viewmodels.GroupViewModel
import androidx.compose.ui.draw.clip
import fi.lauriari.traveljournal.GetGroupQuery
import fi.lauriari.traveljournal.data.models.Member

@Composable
fun AddMemberDialog(
    context: Context,
    groupViewModel: GroupViewModel,
    openAddMemberDialog: MutableState<Boolean>,
    onSearchMembersPressed: () -> Unit,
    onAddMemberPressed: () -> Unit,
    searchUsersData: APIRequestState<List<SearchUsersQuery.SearchUser?>?>,
) {
    Dialog(
        onDismissRequest = {
            openAddMemberDialog.value = false
        },
        content = {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
                    .background(Color.White),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp),
                            style = MaterialTheme.typography.h6,
                            text = "Add members",
                            fontSize = 20.sp
                        )
                    }
                    OutlinedTextField(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                        shape = CircleShape,
                        label = {
                            Text("Search..")
                        },
                        value = groupViewModel.searchInputState.value,
                        onValueChange = { newtext ->
                            groupViewModel.searchInputState.value = newtext
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    if (groupViewModel.searchInputState.value.length >= 3) {
                                        onSearchMembersPressed()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Use atleast 3 letters to search with",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search icon",
                                    tint = Color.Black
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (groupViewModel.searchInputState.value.length >= 3) {
                                    onSearchMembersPressed()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Use atleast 3 letters to search with",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                        )
                    )
                    when (val data: APIRequestState<List<SearchUsersQuery.SearchUser?>?> =
                        searchUsersData) {
                        is APIRequestState.Success -> {
                            Column(
                                modifier = Modifier.padding(5.dp)
                            ) {

                                val dataIds = groupViewModel.groupMembers?.map { user ->
                                    user?.id
                                }
                                val filteredList: MutableList<Member> = mutableListOf()

                                data.response?.forEach { user ->
                                    if (!dataIds?.contains(user?.id)!! &&
                                        groupViewModel.userId != user?.id
                                    ) {
                                        filteredList.add(Member(user?.id!!, user.username!!))
                                    }
                                }
                                UserSearchLazyColumn(filteredList)
                            }
                        }
                        is APIRequestState.BadResponse -> {
                            Text(data.error)
                        }
                        else -> {}
                    }
                }
            }
        }
    )
}

@Composable
fun UserSearchLazyColumn(
    data: MutableList<Member>
) {
    LazyColumn {
        items(data) { user ->
            Row {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val usernameStartingLetter =
                            user.username[0].toString()
                                .uppercase()

                        Text(
                            text = usernameStartingLetter,
                            fontSize = 12.sp
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .size(40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = user.username
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        shape = CircleShape
                    )
                    {
                        Text("Add")
                    }
                }
            }
        }
    }
}