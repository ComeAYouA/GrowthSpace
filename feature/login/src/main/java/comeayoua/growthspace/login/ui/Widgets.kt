package comeayoua.growthspace.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun LoginDivider(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = modifier
                .height(0.3.dp)
                .weight(1f)
                .background(Color.Gray)
        )

        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = "or sign in with",
            color = Color.Gray,
            fontSize = 12.sp
        )

        Spacer(
            modifier = modifier
                .height(0.3.dp)
                .weight(1f)
                .background(Color.Gray)
        )

    }
}

@Composable
internal fun CreateNewSuggestion(
    modifier: Modifier = Modifier,
    toSignUpForm: () -> Unit = {}
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Don`t have an account",
            color = Color.Gray,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier.clickable { toSignUpForm() },
            text = "Create an account",
            color = Color.Blue,
            fontSize = 12.sp
        )
    }
}