package comeayoua.growthspace.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import comeayoua.growthspace.core.ui.R

@Preview
@Composable
fun SignInWithGoogleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isLoading: () -> Boolean = { false }
){
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp),
        enabled = !isLoading.invoke(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            disabledContentColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ){
        Box(modifier = Modifier.fillMaxWidth()){
            if (!isLoading.invoke()){
                Image (
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                        .size(32.dp)
                        .align(Alignment.CenterStart),
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Continue with Google",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                CircularProgressIndicator(modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center))
            }
        }
    }
}
