package fi.lauriari.traveljournal.screens.group

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RemoveLinkDialog(
    openRemoveLinkDialog: MutableState<Boolean>,
    onRemoveLinkPressed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            openRemoveLinkDialog.value = false
        },
        title = {
            Text(text = "Remove link?")
        },
        confirmButton = {
            Button(
                modifier = Modifier.padding(start = 25.dp, bottom = 15.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    openRemoveLinkDialog.value = false
                    onRemoveLinkPressed()
                }) {
                Text("Remove")
            }
        },
        dismissButton = {
            OutlinedButton(
                modifier = Modifier.padding(bottom = 15.dp),
                shape = CircleShape,
                onClick = {
                    openRemoveLinkDialog.value = false
                }) {
                Text("Dismiss")
            }
        }
    )
}