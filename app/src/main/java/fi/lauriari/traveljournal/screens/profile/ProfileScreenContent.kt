package fi.lauriari.traveljournal.screens.profile

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import fi.lauriari.traveljournal.ui.theme.backGroundBlue

@Composable
fun ProfileScreenContent(
    navigateToLoginScreen: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(color = backGroundBlue)
            .fillMaxSize(),
    ) {


        val openDialog = remember { mutableStateOf(true) }

        if (openDialog.value) {
            AddGroupDialog(
                context = context,
                openDialog = openDialog,
            )
        }

        ProfileTopRow(
            navigateToLoginScreen = navigateToLoginScreen,
            openDialog = {
                openDialog.value = true
            }
        )
        ProfileIndicator(context = context)
        ProfileName(context = context)
    }
}