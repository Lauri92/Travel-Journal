package fi.lauriari.traveljournal.screens.login

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginTextField(
    placeholderText: String,
    textState: String,
    onTextChanged: (String) -> Unit,
    isInputAllowed: Boolean,
    textVisibility: Boolean,
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
        },
        visualTransformation = if (textVisibility) VisualTransformation.None else PasswordVisualTransformation(),
    )
}