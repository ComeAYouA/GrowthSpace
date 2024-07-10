package comeayoua.growthspace.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
fun SignUpForm(
    modifier: Modifier = Modifier,
    isSigningUp: () -> Boolean,
    emailQueue: MutableState<String> = rememberSaveable { mutableStateOf("") },
    passwordQueue: MutableState<String> = rememberSaveable { mutableStateOf("") },
    confirmPasswordQueue: MutableState<String> = rememberSaveable { mutableStateOf("") },
    onSignUp: (String, String) -> Unit,
    formState: FormState = remember { FormState.Valid },
    updateForm: (FormState) -> Unit,
    isSyncingWithGoogle: () -> Boolean = { false },
    signInWithGoogle: () -> Unit = {}
){
    val spacerModifier = Modifier
        .fillMaxWidth()
        .height(16.dp)

    LaunchedEffect(formState) {
        emailQueue.value = ""
        passwordQueue.value = ""
        confirmPasswordQueue.value = ""
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = formState.let { state ->
                if (state is FormState.Error) state.message else ""
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
            }
            ,
            queue = emailQueue
        )
        Spacer(modifier = spacerModifier)
        DefaultTextField(
            hint = stringResource(id = R.string.Password),
            hintColor = Color.Gray,
            queue = passwordQueue,
            onValueChanged = { str ->
                if (str.isNotEmpty() && formState is FormState.Error)
                    updateForm(FormState.Valid)
            }
            ,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = spacerModifier)
        DefaultTextField(
            hint = stringResource(id = R.string.ConfirmPassword),
            hintColor = Color.Gray,
            queue = confirmPasswordQueue,
            onValueChanged = { str ->
                if (str.isNotEmpty() && formState is FormState.Error)
                    updateForm(FormState.Valid)
            }
            ,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = spacerModifier)

        Box(
            modifier = Modifier
                .height(42.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable(!isSigningUp()) {
                    onSignUp(emailQueue.value, passwordQueue.value)
                },
            contentAlignment = Alignment.Center
        ) {
            if (isSigningUp()) {
                CircularProgressIndicator( color = MaterialTheme.colorScheme.onPrimary )
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
            onClick = signInWithGoogle,
            isLoading = isSyncingWithGoogle
        )
    }
}
