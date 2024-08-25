package comeayoua.growthspace.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import comeayoua.growthspace.core.ui.R
import comeayoua.growthspace.login.LoginScreenState
import comeayoua.growthspace.login.model.SignInInfo
import comeayoua.growthspace.login.model.SignUpInfo
import comeayoua.growthspace.ui.theme.Blue80
import comeayoua.growthspace.ui.widgets.CenterTextDivider
import comeayoua.growthspace.ui.widgets.DefaultTextField
import comeayoua.growthspace.ui.widgets.ShowContentButton
import comeayoua.growthspace.ui.widgets.SignInWithGoogleButton

@Composable
internal fun LoginForm(
    modifier: Modifier = Modifier,
    uiState: State<LoginScreenState>,
    confirmPasswordField: Boolean = true,
    createNewAccountLink: Boolean = true,
    onLogin: (String, String, String?) -> Unit,
    signInWithGoogle: () -> Unit = {},
    toAnotherForm: () -> Unit = {}
){
    val emailQueue: MutableState<String> = rememberSaveable { mutableStateOf("") }
    val passwordQueue: MutableState<String> = rememberSaveable { mutableStateOf("") }
    val showPassword: MutableState<Boolean> = remember { mutableStateOf(false) }

    val confirmPasswordQueue: MutableState<String>? = if (confirmPasswordField){
        rememberSaveable {
            mutableStateOf("")
        }
    } else null

    val showConfirmPassword: MutableState<Boolean>? = if (confirmPasswordField) {
        remember {
            mutableStateOf(false)
        }
    } else null

    val spacerModifier = Modifier
        .fillMaxWidth()
        .height(16.dp)

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(modifier = spacerModifier)

        DefaultTextField(
            hint = stringResource(id = R.string.Email),
            hintColor = Color.Gray,
            queue = emailQueue
        )
        Spacer(modifier = spacerModifier)

        Box{
            DefaultTextField(
                hint = stringResource(id = R.string.Password),
                hintColor = Color.Gray,
                queue = passwordQueue,
                visualTransformation = if (showPassword.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )

            ShowContentButton(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(24.dp)
                    .align(Alignment.CenterEnd),
                onClick = { showPassword.value = !showPassword.value },
                contentVisible = showPassword.value
            )
        }

        Spacer(modifier = spacerModifier)

        if (confirmPasswordField){
            Box{
                DefaultTextField(
                    hint = stringResource(id = R.string.ConfirmPassword),
                    hintColor = Color.Gray,
                    queue = confirmPasswordQueue!!,
                    visualTransformation = if (showConfirmPassword!!.value){
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    }
                )

                ShowContentButton(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(24.dp)
                        .align(Alignment.CenterEnd),
                    onClick = { showConfirmPassword.value = !showConfirmPassword.value },
                    contentVisible = showConfirmPassword.value
                )
            }
            Spacer(modifier = spacerModifier)
        }

        Box(
            modifier = Modifier
                .height(42.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable(uiState.value !is LoginScreenState.Loading) {
                    onLogin(emailQueue.value, passwordQueue.value, confirmPasswordQueue?.value)
                },
            contentAlignment = Alignment.Center
        ) {
            if (uiState.value is LoginScreenState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = stringResource(id = R.string.SignUp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = spacerModifier)
        CenterTextDivider(text = "or ${stringResource(id = R.string.SignIn).lowercase()}")
        Spacer(modifier = spacerModifier)

        SignInWithGoogleButton(
            modifier = Modifier,
            onClick = signInWithGoogle,
            isLoading = { uiState.value is LoginScreenState.SyncingWithGoogle }
        )


        if (createNewAccountLink) {
            Spacer(modifier = spacerModifier)
            CreateNewSuggestion(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                toSignUpForm = toAnotherForm
            )
        }
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
            color = Blue80,
            fontSize = 12.sp
        )
    }
}

@Composable
internal fun SignInForm(
    modifier: Modifier = Modifier,
    onSignIn: (SignInInfo) -> Unit,
    signInWithGoogle: () -> Unit,
    toSignUpForm: () -> Unit,
    uiState: State<LoginScreenState>
){
    LoginForm(
        modifier = modifier,
        confirmPasswordField = false,
        createNewAccountLink = true,
        onLogin = { email, password, _ ->
            onSignIn(SignInInfo(email, password))
        },
        signInWithGoogle = signInWithGoogle,
        toAnotherForm = toSignUpForm,
        uiState = uiState
    )
}

@Composable
internal fun SignUpForm(
    modifier: Modifier = Modifier,
    onSignUp: (SignUpInfo) -> Unit,
    signInWithGoogle: () -> Unit,
    uiState: State<LoginScreenState>
){
    LoginForm(
        modifier = modifier,
        confirmPasswordField = true,
        createNewAccountLink = false,
        onLogin = { email, password, confirmPassword ->
            onSignUp(SignUpInfo(email, password, confirmPassword!!))
        },
        signInWithGoogle = signInWithGoogle,
        uiState = uiState
    )
}
