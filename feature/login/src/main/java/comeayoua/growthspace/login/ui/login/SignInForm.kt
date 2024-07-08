package comeayoua.growthspace.login.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.decapitalize
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import comeayoua.growthspace.core.ui.R
import comeayoua.growthspace.login.ui.CreateNewSuggestion
import comeayoua.growthspace.ui.widgets.CenterTextDivider
import comeayoua.growthspace.ui.widgets.DefaultTextField
import comeayoua.growthspace.ui.widgets.SignInWithGoogleButton
import java.util.Locale

@Composable
fun SignInForm(
    modifier: Modifier = Modifier,
    emailQueue: MutableState<String> = rememberSaveable { mutableStateOf("") },
    passwordQueue: MutableState<String> = rememberSaveable { mutableStateOf("") },
    toSignUpForm: () -> Unit = {},
    onLogin: () -> Unit = {},
    isLoading: () -> Boolean = { false },
    signInWithGoogle: () -> Unit = {}
){

    val spacerModifier = Modifier
        .fillMaxWidth()
        .height(16.dp)
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        DefaultTextField(hint = stringResource(id = R.string.Email), query = emailQueue,)
        Spacer(modifier = spacerModifier)
        DefaultTextField(
            hint = stringResource(id = R.string.Password),
            query = passwordQueue,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = spacerModifier)

        Box(
            modifier = Modifier
                .height(42.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onLogin() }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.Login),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = spacerModifier)
        CenterTextDivider(text = "or ${stringResource(id = R.string.Login).lowercase()}")
        Spacer(modifier = spacerModifier)

        SignInWithGoogleButton(
            onClick = signInWithGoogle,
            isLoading = isLoading
        )

        Spacer(modifier = spacerModifier)

        CreateNewSuggestion(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            toSignUpForm = toSignUpForm
        )
    }
}