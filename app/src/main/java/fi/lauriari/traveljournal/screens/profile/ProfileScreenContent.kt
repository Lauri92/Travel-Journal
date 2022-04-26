package fi.lauriari.traveljournal.screens.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.lauriari.traveljournal.GetGroupsByUserIdQuery
import fi.lauriari.traveljournal.ui.theme.backGroundBlue
import fi.lauriari.traveljournal.util.APIRequestState
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

@Composable
fun ProfileScreenContent(
    profileViewModel: ProfileViewModel,
    navigateToLoginScreen: () -> Unit,
    navigateToGroupScreen: (String) -> Unit,
    openAddGroupDialog: () -> Unit,
    getGroupsByUserIdData: APIRequestState<List<GetGroupsByUserIdQuery.GetGroupsByUserId?>?>,
    openChangeProfileImageDialog: () -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(color = backGroundBlue)
            .fillMaxSize(),
    ) {
        ProfileTopRow(
            navigateToLoginScreen = navigateToLoginScreen,
            openAddGroupDialog = { openAddGroupDialog() }
        )
        ProfileIndicator(
            context = context,
            openChangeProfileImageDialog = { openChangeProfileImageDialog() }
        )
        ProfileName(context = context)

        when (getGroupsByUserIdData) {
            is APIRequestState.Loading -> {
                Column(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(
                            width = 200.dp,
                            height = 200.dp
                        ),
                        strokeWidth = 10.dp
                    )
                    Text(
                        modifier = Modifier.padding(top = 100.dp),
                        text = "Loading your groups...",
                        fontSize = 25.sp,
                    )
                }
            }
            is APIRequestState.Success -> {
                Grouplist(
                    context = context,
                    profileViewModel = profileViewModel,
                    groupList = getGroupsByUserIdData.response,
                    navigateToGroupScreen = navigateToGroupScreen
                )
            }
            is APIRequestState.BadResponse -> {
                Column(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Something went wrong loading groups!")
                }
            }
            is APIRequestState.EmptyList -> {
                Column(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("You are not part of any group yet!")
                }
            }
            is APIRequestState.Idle -> {}
        }
    }
}

@Composable
fun MemberlistCircle(
    username: String?,
    startPadding: Dp
) {
    Box(
        modifier = Modifier
            .padding(start = startPadding)
            .size(20.dp)
            .clip(CircleShape)
            .background(Color.Gray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val usernameStartingLetter =
                username?.get(0).toString().uppercase()

            Text(
                text = usernameStartingLetter,
                fontSize = 10.sp
            )
        }
    }

}

@Composable
fun Grouplist(
    profileViewModel: ProfileViewModel,
    context: Context,
    groupList: List<GetGroupsByUserIdQuery.GetGroupsByUserId?>?,
    navigateToGroupScreen: (String) -> Unit,
) {
    Text(
        modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp),
        text = "Your groups",
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )
    LazyColumn() {
        items(groupList!!) { group ->
            Column(
                Modifier
                    .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 20.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .clickable {
                        navigateToGroupScreen(group?.id.toString())
                    }
            ) {
                val members = group!!.members!!.size + 1

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = group.name!!,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (profileViewModel.userId == group.admin?.id) {
                        Icon(
                            modifier = Modifier.padding(5.dp),
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Admin indicator",
                            tint = Color.Red
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Members ($members)",
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
                Row {
                    MemberlistCircle(
                        username = group.admin!!.username,
                        startPadding = 5.dp
                    )
                    group.members?.forEach { member ->
                        MemberlistCircle(
                            username = member?.username,
                            startPadding = 2.dp
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = group.description!!
                )
            }
        }
    }
}