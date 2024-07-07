package comeayoua.growthspace.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import comeayoua.growthspace.feature.login.R
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    onLogin: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ){

        Column {
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))

            Text(
                text = "Get ready for a world for a growing spaces",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 32.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                LoginForm(
                    modifier = Modifier,
                    isEnabled = { uiState is LoginScreenState.Enabled },
                    signInWithGoogle = { coroutineScope.launch {
                        if (viewModel.signIn(context)) onLogin()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SignInButton(
    onClick: () -> Unit,
    isEnabled: () -> Boolean = { true }
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(60.dp)),
        enabled = isEnabled.invoke(),
        onClick = onClick,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)){
            if (isEnabled.invoke()){
                Image (
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                        .size(24.dp)
                        .align(Alignment.CenterStart),
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Continue with Google",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            } else {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    isEnabled: () -> Boolean,
    signInWithGoogle: () -> Unit = {}
){
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SearchBar(hint = "Email")
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(16.dp))
        SearchBar(hint = "Password")
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(16.dp))

        Box(
            modifier = Modifier
                .height(42.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable { }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Login"
                )
            }
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
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

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(16.dp))

        SignInButton(
            onClick = signInWithGoogle,
            isEnabled = isEnabled
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(8.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don`t have an account",
                color = Color.Gray,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Create an account",
                color = Color.Blue,
                fontSize = 12.sp
            )
        }
    }
}
@Composable
internal fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit = {},
    hint: String = "",
    searchQuery: MutableState<String> = rememberSaveable { mutableStateOf("") }
){
    BasicTextField(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(30.dp))
            .height(42.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        value = searchQuery.value,
        onValueChange = {
            searchQuery.value = it
            onSearchQueryChanged(it)
        },
        singleLine = true,
        textStyle = TextStyle().copy(
            fontSize = 16.sp
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ){
                if (searchQuery.value.isEmpty()){
                    Text(
                        text = hint,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        }
    )
}