package fi.lauriari.traveljournal.screens.login

import android.util.Log
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginTextField(
    placeholderText: String,
    textState: String,
    onTextChanged: (String) -> Unit,
    isInputAllowed: Boolean,
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