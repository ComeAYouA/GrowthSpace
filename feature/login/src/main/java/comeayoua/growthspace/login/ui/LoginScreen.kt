package comeayoua.growthspace.login.ui

import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import comeayoua.growthspace.core.ui.R
import comeayoua.growthspace.login.LoginScreenState
import comeayoua.growthspace.login.LoginViewModel
import comeayoua.growthspace.login.model.SignInInfo
import comeayoua.growthspace.login.model.SignUpInfo
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogin: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState { 2 }

    val openErrorSheet = rememberSaveable { mutableStateOf(false) }
    val errorSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val openSuccessSheet = rememberSaveable { mutableStateOf(false) }
    val successSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val signInAction: (
        signInInfo: SignInInfo
    ) -> Unit = remember {
        { signInInfo ->
            coroutineScope.launch {
                with(signInInfo) {
                    viewModel.signIn(email, password, onLogin)
                }
            }
        }
    }

    val signUpAction: (
        signUpInfo: SignUpInfo
    )-> Unit = remember{
        { signUpInfo ->
            coroutineScope.launch {
                with(signUpInfo){
                    viewModel.signUp(email, password, confirmPassword, onLogin)
                }
            }
        }
    }

    val signInWithGoogleAction: () -> Unit = remember{
        { coroutineScope.launch { viewModel.signInWithGoogle(context, onLogin) } }
    }

    val toSignInFormAction: () -> Unit = remember{
        { coroutineScope.launch{pagerState.animateToAnotherForm(1) } }
    }

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

    LaunchedEffect(key1 = uiState.value) {
        when(uiState.value) {
            is LoginScreenState.Error -> {
                openSuccessSheet.value = false
                openErrorSheet.value = true
                errorSheetState.show()
            }

            is LoginScreenState.Success -> {
                openErrorSheet.value = false
                openSuccessSheet.value = true
                successSheetState.show()
            }
            else -> {}
        }
    }

    if (openErrorSheet.value || openSuccessSheet.value){
        uiState.value.let {  state ->
            when(state){
                is LoginScreenState.Success -> SuccessBottomSheet(
                    title = "Login success",
                    message = null,
                    onDismissRequest = { openSuccessSheet.value = false },
                    sheetState = successSheetState
                )
                is LoginScreenState.Error -> ErrorBottomSheet(
                    title = "Login failed",
                    message = state.message,
                    onDismissRequest = {
                        openErrorSheet.value = false
                        viewModel.dismissError()
                    },
                    sheetState = errorSheetState
                )
                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
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

            if (pagerState.currentPage == 1){
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateToAnotherForm(0)
                            }
                        },
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "back to sign in"
                )
            }


            Column {
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(R.string.Login_lead),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 32.sp
                )

                RegistrationPager(
                    pagerState = pagerState,
                    uiState = uiState,
                    signInAction = signInAction,
                    signUpAction = signUpAction,
                    signInWithGoogleAction = signInWithGoogleAction,
                    toSignInFormAction = toSignInFormAction
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegistrationPager(
    pagerState: PagerState,
    uiState: State<LoginScreenState>,
    signInAction: (SignInInfo) -> Unit,
    signUpAction: (SignUpInfo) -> Unit,
    signInWithGoogleAction: () -> Unit,
    toSignInFormAction: () -> Unit,
){
    Box(
        modifier = Modifier
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
        ) { page ->
            when(page){
                0 -> {
                    SignInForm(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onSignIn = signInAction,
                        signInWithGoogle = signInWithGoogleAction,
                        toSignUpForm = toSignInFormAction,
                        uiState
                    )
                }
                1 -> {
                    SignUpForm(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onSignUp = signUpAction,
                        signInWithGoogle = signInWithGoogleAction,
                        uiState
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
internal suspend fun PagerState.animateToAnotherForm(
    page: Int
){
    this.animateScrollToPage(
        page,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow
        )
    )
}
