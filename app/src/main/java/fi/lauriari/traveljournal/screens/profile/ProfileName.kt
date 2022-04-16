package fi.lauriari.traveljournal.screens.profile

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.lauriari.traveljournal.util.User

@Composable
fun ProfileName(
    context: Context
) {
    Column(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = User.getUsername(context) ?: "Some name",
            fontSize = 25.sp,
            fontStyle = FontStyle.Italic
        )
    }
}