package comeayoua.growthspace.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import comeayoua.growthspace.core.ui.R
import comeayoua.growthspace.login.LoginScreenState
import comeayoua.growthspace.login.LoginViewModel
import comeayoua.growthspace.ui.widgets.SignInWithGoogleButton


@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()
    val layoutDirection = LocalLayoutDirection.current
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(
                    bottom = paddingValues.calculateBottomPadding(),
                    end = paddingValues.calculateEndPadding(layoutDirection),
                    start = paddingValues.calculateStartPadding(layoutDirection),

                )
                .fillMaxSize()
        ){
            LoginContent(
                uiState = uiState,
                onLogin = { viewModel.signInWithGoogle(context, onLogin) }
            )
        }
    }
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    uiState: State<LoginScreenState>,
    onLogin: () -> Unit = {}
){
    val gradient = Brush.verticalGradient(
        0.0f to Color.Transparent,
        0.3f to MaterialTheme.colorScheme.background
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 128.dp),
            painter = painterResource(id = R.mipmap.img_login_background),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(gradient)
                .padding(horizontal = 16.dp, vertical = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Log in to your Google account to save your progress and share habits with friends",
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            SignInWithGoogleButton(
                modifier = Modifier.padding(top = 16.dp),
                onClick = onLogin,
                isLoading = { uiState.value is LoginScreenState.SyncingWithGoogle }
            )
        }
    }
}