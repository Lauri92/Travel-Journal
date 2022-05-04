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
fun DeleteGroupDialog(
    groupViewModel: GroupViewModel,
    openDeleteGroupDialog: MutableState<Boolean>,
    onDeleteGroupPressed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            openDeleteGroupDialog.value = false
        },
        title = {
            Text(text = "Remove the group?")
        },
        text = {
            Text("Remove the group? This cannot be undone!")
        },
        confirmButton = {
            Button(
                modifier = Modifier.padding(start = 25.dp, bottom = 15.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    openDeleteGroupDialog.value = false
                    onDeleteGroupPressed()
                    groupViewModel.messages.clear()
                }) {
                Text("Remove")
            }
        },
        dismissButton = {
            OutlinedButton(
                modifier = Modifier.padding(bottom = 15.dp),
                shape = CircleShape,
                onClick = {
                    openDeleteGroupDialog.value = false
                }) {
                Text("Dismiss")
            }
        }
    )
}