package fi.lauriari.traveljournal.screens.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun GroupScreenContentHeader(
    navigateToProfileScreen: () -> Unit,
    getGroupByIdData: APIRequestState.Success<GetGroupQuery.GetGroup?>
) {
    Row {
        IconButton(onClick = { navigateToProfileScreen() }) {
            Icon(
                modifier = Modifier.padding(10.dp),
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Admin indicator",
                tint = Color.Black
            )
        }
    }
    Row {
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