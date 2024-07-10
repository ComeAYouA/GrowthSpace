package comeayoua.growthspace.login.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import comeayoua.growthspace.core.ui.R
import comeayoua.growthspace.login.LoginScreenState
import comeayoua.growthspace.login.LoginViewModel
import comeayoua.growthspace.login.ui.stateholders.FormState
import comeayoua.growthspace.login.ui.stateholders.LoginMode
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLogin: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val loginMode: MutableState<LoginMode> = remember {
        mutableStateOf(LoginMode.SignIn)
    }

    LaunchedEffect(loginMode.value) {
        viewModel.updateFormState(FormState.Valid)
    }

    val formState by viewModel.formState.collectAsState()

    val gradient = Brush.radialGradient(
        0.0f to MaterialTheme.colorScheme.primaryContainer,
        1f to MaterialTheme.colorScheme.background
    )
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val gradientAnim by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(
                durationMillis = 3000,
                easing = EaseInOutQuad
            )
        ),
        label = ""
    )

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val windowWidth = with(density){ configuration.screenWidthDp.toDp().toPx().toInt() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ){
        Spacer(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = gradientAnim
                    scaleY = gradientAnim
                }
                .fillMaxSize()
                .background(gradient)
                .align(Alignment.Center)
        )

        if (loginMode.value is LoginMode.SignUp){
            Icon(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable { loginMode.value = LoginMode.SignIn },
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "back to sign in"
            )
        }


        Column {
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))

            Text(
                text = stringResource(R.string.Login_lead),
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 32.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {

                this@Column.AnimatedVisibility(
                    visible = loginMode.value is LoginMode.SignIn,
                    enter = slideInHorizontally { -windowWidth * 3 },
                    exit = slideOutHorizontally { -windowWidth * 3 }
                ) {
                    SignInForm(
                        modifier = Modifier,
                        isLoginning = { uiState is LoginScreenState.LoginningUp},
                        isSyncingWithGoogle = { uiState is LoginScreenState.SyncingWithGoogle },
                        onLogin = { email, password ->
                            coroutineScope.launch {
                                if (viewModel.signIn(email, password)) onLogin()
                            }
                        },
                        signInWithGoogle = {
                            coroutineScope.launch { if (viewModel.signInWithGoogle(context)) onLogin() }
                        },
                        updateForm = {formState ->  viewModel.updateFormState(formState)},
                        toSignInForm = { loginMode.value = LoginMode.SignUp },
                        formState = formState,
                    )
                }
                this@Column.AnimatedVisibility(
                    visible = loginMode.value is LoginMode.SignUp,
                    enter = slideInHorizontally { windowWidth * 3},
                    exit = slideOutHorizontally { windowWidth * 3 }
                ) {
                    SignUpForm(
                        modifier = Modifier,
                        isSigningUp = { uiState is LoginScreenState.SigningUp},
                        isSyncingWithGoogle = { uiState is LoginScreenState.SyncingWithGoogle },
                        onSignUp = { email, password ->
                            coroutineScope.launch {
                                if(viewModel.signUp(email, password)) onLogin()
                            }
                        },
                        updateForm = {formState ->  viewModel.updateFormState(formState)},
                        signInWithGoogle = {
                            coroutineScope.launch {
                                if (viewModel.signInWithGoogle(context)) onLogin()
                            }
                        },
                        formState = formState
                    )
                }
            }
        }
    }
}
