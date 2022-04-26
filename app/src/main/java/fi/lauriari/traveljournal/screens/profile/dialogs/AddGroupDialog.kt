package fi.lauriari.traveljournal.screens.profile.dialogs

import android.content.Context
import android.widget.Toast
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
import fi.lauriari.traveljournal.viewmodels.ProfileViewModel

@Composable
fun AddGroupDialog(
    context: Context,
    openAddGroupDialog: MutableState<Boolean>,
    onAddGroupPressed: () -> Unit,
    profileViewModel: ProfileViewModel,
) {
    Dialog(
        onDismissRequest = {
            openAddGroupDialog.value = false
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
                        text = "Add a group",
                        fontSize = 20.sp
                    )
                    OutlinedTextField(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        label = {
                            Text("Group name")
                        },
                        value = profileViewModel.groupNameTextState.value,
                        onValueChange = { newGroupName ->
                            if (newGroupName.length <= 15) {
                                profileViewModel.groupNameTextState.value = newGroupName
                            } else {
                                Toast.makeText(
                                    context,
                                    "Maximum length for name is 15 characters",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    OutlinedTextField(
                        modifier = Modifier.padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 10.dp
                        ),
                        maxLines = 3,
                        label = {
                            Text("Description")
                        },
                        value = profileViewModel.descriptionTextState.value,
                        onValueChange = { newDescription ->
                            if (newDescription.length <= 50) {
                                profileViewModel.descriptionTextState.value = newDescription
                            } else {
                                Toast.makeText(
                                    context,
                                    "Limit is 50 characters",
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
                            onClick = { openAddGroupDialog.value = false }) {
                            Text(text = "Dismiss")
                        }
                        Button(
                            shape = CircleShape,
                            onClick = {
                                openAddGroupDialog.value = false
                                onAddGroupPressed()
                            })
                        {
                            Text(text = "Create")
                        }
                    }
                }
            }
        }
    )
}
