package fi.lauriari.traveljournal.screens.group

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.lauriari.traveljournal.viewmodels.GroupViewModel

@Composable
fun AddRow(
    groupViewModel: GroupViewModel,
    membersSelected: MutableState<Boolean>,
    linksSelected: MutableState<Boolean>,
    adminId: String?,
    openAddLinkDialog: MutableState<Boolean>,
    openAddMemberDialog: MutableState<Boolean>,
    openUploadGroupImageDialog: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        when {
            membersSelected.value -> {
                val enabled = groupViewModel.userId == adminId
                OutlinedButton(
                    modifier = Modifier
                        .size(width = 200.dp, height = 60.dp),
                    shape = CircleShape,
                    onClick = { openAddMemberDialog.value = true },
                    enabled = enabled,
                ) {
                    Text(text = "Add a member", fontSize = 20.sp)
                }
            }
            linksSelected.value -> {
                OutlinedButton(
                    modifier = Modifier
                        .size(width = 200.dp, height = 60.dp),
                    shape = CircleShape,
                    onClick = { openAddLinkDialog.value = true }) {
                    Text(text = "Add a link", fontSize = 20.sp)
                }
            }
            else -> {
                OutlinedButton(
                    modifier = Modifier
                        .size(width = 200.dp, height = 60.dp),
                    shape = CircleShape,
                    onClick = { openUploadGroupImageDialog.value = true }) {
                    Text(text = "Add a file", fontSize = 20.sp)
                }
            }
        }
    }
}