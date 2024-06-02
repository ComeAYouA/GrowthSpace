package comeayoua.growthspace.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle

@Composable
fun SignIn(
    modifier: Modifier = Modifier,
) {

    Box(modifier = modifier.fillMaxSize()){
        Text(modifier = Modifier.align(Alignment.Center), text = "Login")
    }

//    val uiState = viewModel.uiState.collectAsState()
//
//    val signInAction = viewModel.composeAuth.rememberSignInWithGoogle(
//        onResult = { result -> with(viewModel){ result.retrieveGoogleAuth() } },
//        fallback = {}
//    )
//
//    Box(
//        modifier = modifier,
//        contentAlignment = Alignment.Center
//    ){
//        uiState.value.let { state ->
//            when(state){
//                LoginScreenState.Loading -> {
//                    CircularProgressIndicator()
//                }
//                LoginScreenState.LoggedIn -> {
//                    Text("You are already logged in")
//                }
//                LoginScreenState.OnBoarding -> {
//                    Box(modifier = modifier.fillMaxSize()){
//                        Button(
//                            onClick = {viewModel.signIn()}
//                        ){
//                            Text("Sign in with Google")
//                        }
//                    }
//                }
//            }
//        }
//    }

}