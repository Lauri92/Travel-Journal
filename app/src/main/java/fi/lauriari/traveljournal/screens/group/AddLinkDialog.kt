package fi.lauriari.traveljournal.screens.group

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import fi.lauriari.traveljournal.viewmodels.GroupViewModel

@Composable
fun AddLinkDialog(
    groupViewModel: GroupViewModel,
    openLinkDialog: MutableState<Boolean>,
    onAddLinkPressed: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            openLinkDialog.value = false
        },
        content = {
            Box(
                modifier = Modifier
                    .size(300.dp, 175.dp)
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        style = MaterialTheme.typography.h6,
                        text = "Add a Link",
                        fontSize = 20.sp
                    )
                    OutlinedTextField(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        label = {
                            Text("Link url")
                        },
                        value = groupViewModel.urlTextState.value,
                        onValueChange = { newtext ->
                            groupViewModel.urlTextState.value = newtext
                        }
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        OutlinedButton(
                            shape = CircleShape,
                            onClick = { openLinkDialog.value = false }) {
                            Text(text = "Dismiss")
                        }
                        Button(
                            shape = CircleShape,
                            onClick = {
                                openLinkDialog.value = false
                                onAddLinkPressed()
                            })
                        {
                            Text(text = "Add")
                        }
                    }
                }
            }
        }
    )
}