package fi.lauriari.traveljournal.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fi.lauriari.traveljournal.util.User
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

@Composable
fun ProfileTopRow(
    navigateToLoginScreen: () -> Unit,
    openAddGroupDialog: () -> Unit,
    profileViewModel: ProfileViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            val context = LocalContext.current
            IconButton(onClick = {
                profileViewModel.getGroupsByUserId(context)
            }) {
                Icon(
                    modifier = Modifier.padding(10.dp),
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Refresh the page",
                    tint = Color.Black
                )
            }
            OutlinedButton(
                modifier = Modifier
                    .padding(end = 10.dp, top = 10.dp),
                shape = CircleShape,
                onClick = {
                    openAddGroupDialog()
                }
            ) {
                Text(
                    text = "Add a group",
                    color = Color.Black
                )
            }
            OutlinedButton(
                modifier = Modifier
                    .padding(end = 10.dp, top = 10.dp),
                shape = CircleShape,
                onClick = {
                    User.removeToken(context)
                    navigateToLoginScreen()
                }
            ) {
                Text(
                    text = "Logout",
                    color = Color.Black
                )
            }
        }
    }
}


