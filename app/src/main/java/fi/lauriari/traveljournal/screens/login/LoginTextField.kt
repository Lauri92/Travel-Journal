package fi.lauriari.traveljournal.screens.login

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginTextField(
    placeholderText: String,
    textState: String,
    onTextChanged: (String) -> Unit,
    isInputAllowed: Boolean
) {
    OutlinedTextField(
        enabled = isInputAllowed,
        singleLine = true,
        label = {
            Text(
                text = placeholderText
            )
        },
        value = textState,
        onValueChange = onTextChanged,
        placeholder = {
            Text(
                text = placeholderText
            )
        }
    )
}