package fi.lauriari.traveljournal.screens.group

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                if (groupViewModel.userId == getGroupByIdData.response?.admin?.id ?: "Shouldn't happen") {
                    AddMembersRow()
                }
                GroupItemsRow()



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
fun AddMembersRow() {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedButton(
            modifier = Modifier
                .size(width = 200.dp, height = 60.dp),
            shape = CircleShape,
            onClick = { /* TODO: Open add members dialog */ }) {
            Text(text = "Add a member", fontSize = 20.sp)
        }
    }
}

@Composable
fun GroupItemsRow() {
    Row(
        modifier = Modifier
            .border(width = 1.dp, color = Color.Gray)
            .padding(15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.clickable {},
            text = "Members",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic
        )
        Text(
            modifier = Modifier.clickable {},
            text = "Links",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic
        )
        Text(
            modifier = Modifier.clickable {},
            text = "Files",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic
        )
    }
}