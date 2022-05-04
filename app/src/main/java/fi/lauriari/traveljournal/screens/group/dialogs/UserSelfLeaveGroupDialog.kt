package fi.lauriari.traveljournal.screens.group.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fi.lauriari.traveljournal.viewmodels.GroupViewModel

@Composable
fun UserSelfLeaveGroupDialog(
    groupViewModel: GroupViewModel,
    openSelfLeaveGroupDialog: MutableState<Boolean>,
    onLeaveGroupPressed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            openSelfLeaveGroupDialog.value = false
        },
        title = {
            Text(text = "Leave group?")
        },
        text = {
            Text("Are you sure you want to leave the group? This cannot be undone.")
        },
        confirmButton = {
            Button(
                modifier = Modifier.padding(start = 25.dp, bottom = 15.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    openSelfLeaveGroupDialog.value = false
                    onLeaveGroupPressed()
                    groupViewModel.messages.clear()
                }) {
                Text("Leave group")
            }
        },
        dismissButton = {
            OutlinedButton(
                modifier = Modifier.padding(bottom = 15.dp),
                shape = CircleShape,
                onClick = {
                    openSelfLeaveGroupDialog.value = false
                }) {
                Text("Dismiss")
            }
        }
    )
}