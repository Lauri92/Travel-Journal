package fi.lauriari.traveljournal.screens.group.dialogs

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import fi.lauriari.traveljournal.viewmodels.GroupViewModel

@Composable
fun ModifyGroupDialog(
    openModifyGroupDialog: MutableState<Boolean>,
    groupViewModel: GroupViewModel,
    onUpdateGroupPressed: () -> Unit
) {
    val context = LocalContext.current
    Dialog(
        onDismissRequest = {
            openModifyGroupDialog.value = false
        },
        content = {
            Box(
                modifier = Modifier
                    .size(300.dp, 250.dp)
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
                        text = "Modify group",
                        fontSize = 20.sp
                    )
                    OutlinedTextField(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        label = {
                            Text("Group name")
                        },
                        value = groupViewModel.nameUpdateTextState.value,
                        onValueChange = { updatedName ->
                            if (updatedName.length <= 15) {
                                groupViewModel.nameUpdateTextState.value = updatedName
                            } else {
                                Toast.makeText(
                                    context,
                                    "Maximum length for name is 15 characters",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    OutlinedTextField(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        label = {
                            Text("Group name")
                        },
                        value = groupViewModel.descriptionUpdateTextState.value,
                        onValueChange = { updatedDescription ->
                            if (updatedDescription.length <= 50) {
                                groupViewModel.descriptionUpdateTextState.value = updatedDescription
                            } else {
                                Toast.makeText(
                                    context,
                                    "Maximum length for name is 50 characters",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        OutlinedButton(
                            shape = CircleShape,
                            onClick = { openModifyGroupDialog.value = false }) {
                            Text(text = "Dismiss")
                        }
                        Button(
                            shape = CircleShape,
                            onClick = {
                                openModifyGroupDialog.value = false
                                onUpdateGroupPressed()
                            })
                        {
                            Text(text = "Update")
                        }
                    }
                }
            }
        }
    )
}