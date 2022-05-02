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
fun DeleteGroupImageDialog(
    openDeleteGroupImageDialog: MutableState<Boolean>,
    onDeleteImagePressed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            openDeleteGroupImageDialog.value = false
        },
        title = {
            Text(text = "Remove image")
        },
        text = {
            Text("Are you sure you want to remove image?")
        },
        confirmButton = {
            Button(
                modifier = Modifier.padding(start = 25.dp, bottom = 15.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    onDeleteImagePressed()
                    openDeleteGroupImageDialog.value = false
                }) {
                Text("Remove image")
            }
        },
        dismissButton = {
            OutlinedButton(
                modifier = Modifier.padding(bottom = 15.dp),
                shape = CircleShape,
                onClick = {
                    openDeleteGroupImageDialog.value = false
                }) {
                Text("Dismiss")
            }
        }
    )
}