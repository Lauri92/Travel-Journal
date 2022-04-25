package fi.lauriari.traveljournal.screens.group.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RemoveUserFromGroupDialog(
    openRemoveFromUserGroupDialog: MutableState<Boolean>,
    onRemoveUserPressed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            openRemoveFromUserGroupDialog.value = false
        },
        title = {
            Text(text = "Remove user?")
        },
        text = {
            Text("Are you sure you want to remove user?")
        },
        confirmButton = {
            Button(
                modifier = Modifier.padding(start = 25.dp, bottom = 15.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    openRemoveFromUserGroupDialog.value = false
                    onRemoveUserPressed()
                }) {
                Text("Remove user")
            }
        },
        dismissButton = {
            OutlinedButton(
                modifier = Modifier.padding(bottom = 15.dp),
                shape = CircleShape,
                onClick = {
                    openRemoveFromUserGroupDialog.value = false
                }) {
                Text("Dismiss")
            }
        }
    )
}