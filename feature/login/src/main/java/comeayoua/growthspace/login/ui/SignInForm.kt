package comeayoua.growthspace.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import comeayoua.growthspace.core.ui.R
import comeayoua.growthspace.login.LoginScreenState
import comeayoua.growthspace.login.ui.stateholders.FormState
import comeayoua.growthspace.ui.widgets.CenterTextDivider
import comeayoua.growthspace.ui.widgets.DefaultTextField
import comeayoua.growthspace.ui.widgets.SignInWithGoogleButton

@Composable
fun SignInForm(
    modifier: Modifier = Modifier,
    formState: FormState = remember { FormState.Valid },
    emailQueue: MutableState<String> = rememberSaveable { mutableStateOf("") },
    passwordQueue: MutableState<String> = rememberSaveable { mutableStateOf("") },
    toSignInForm: () -> Unit = {},
    updateForm: (FormState) -> Unit,
    isLoginning: () -> Boolean,
    isSyncingWithGoogle: () -> Boolean,
    onLogin: (String, String) -> Unit,
    signInWithGoogle: () -> Unit = {}
){
    val spacerModifier = Modifier
        .fillMaxWidth()
        .height(16.dp)

    LaunchedEffect(formState) {
        emailQueue.value = ""
        passwordQueue.value = ""
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = formState.let { state ->
                when(state){
                    is FormState.Error -> state.message
                    FormState.Valid -> ""
                }
            },
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = spacerModifier)

        DefaultTextField(
            hint = stringResource(id = R.string.Email),
            hintColor = Color.Gray,
            onValueChanged = { str ->
                if (str.isNotEmpty() && formState is FormState.Error)
                    updateForm(FormState.Valid)
            },
            queue = emailQueue
        )
        Spacer(modifier = spacerModifier)
        DefaultTextField(
            hint = stringResource(id = R.string.Password),
            hintColor = Color.Gray,
            onValueChanged = { str ->
                if (str.isNotEmpty() && formState is FormState.Error)
                    updateForm(FormState.Valid)
            }
            ,
            queue = passwordQueue,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = spacerModifier)

        Box(
            modifier = Modifier
                .height(42.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable(enabled = !isLoginning()) {
                    onLogin(emailQueue.value, passwordQueue.value)
                },
            contentAlignment = Alignment.Center
        ) {
            if (isLoginning()) {
                CircularProgressIndicator( color = MaterialTheme.colorScheme.onPrimary )
            } else {
                Text(
                    text = stringResource(id = R.string.Login),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = spacerModifier)
        CenterTextDivider(text = "or ${stringResource(id = R.string.SignIn).lowercase()}")
        Spacer(modifier = spacerModifier)

        SignInWithGoogleButton(
            onClick = signInWithGoogle,
            isLoading = isSyncingWithGoogle
        )

        Spacer(modifier = spacerModifier)

        CreateNewSuggestion(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            toSignUpForm = toSignInForm
        )
    }
}