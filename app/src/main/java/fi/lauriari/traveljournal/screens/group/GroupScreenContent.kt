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
import fi.lauriari.traveljournal.ui.theme.backGroundBlue
import fi.lauriari.traveljournal.util.User
import fi.lauriari.traveljournal.viewmodels.GroupViewModel


@Composable
fun GroupScreenContent(
    navigateToProfileScreen: () -> Unit,
    groupViewModel: GroupViewModel
) {
    Column(
        modifier = Modifier
            .background(color = backGroundBlue)
            .fillMaxSize(),
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
                    .background(Color.Red)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val usernameStartingLetter =
                        "Hello".get(0).toString().uppercase()
                    Text(
                        text = usernameStartingLetter,
                        fontSize = 75.sp
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = 15.dp, top = 30.dp)
            ) {
                Text(text = "Group name")
                Text(text = "Group description")
            }
        }
    }
}