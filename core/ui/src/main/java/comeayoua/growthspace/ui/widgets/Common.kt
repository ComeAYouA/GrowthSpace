package comeayoua.growthspace.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CenterTextDivider(
    modifier: Modifier = Modifier,
    text: String
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
            text = text,
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