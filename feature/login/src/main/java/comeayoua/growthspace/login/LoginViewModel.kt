package comeayoua.growthspace.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comeayoua.growthspace.auth.util.UserDataUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.gotrue.Auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val composeAuth: ComposeAuth,
    private val auth: Auth,
    private val userDataUtil: UserDataUtil
): ViewModel() {
    private val _uiState: MutableStateFlow<LoginScreenState> = MutableStateFlow(LoginScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    private fun saveToken(){
        val token = auth.currentAccessTokenOrNull()

        userDataUtil.saveUserToken(token)
    }

    fun NativeSignInResult.retrieveGoogleAuth(){
        when(this){
            NativeSignInResult.Success -> {
                viewModelScope.launch {
                    _uiState.update {
                        LoginScreenState.Loading
                    }

                    saveToken()

                    _uiState.update {
                        LoginScreenState.Success
                    }
                }
            }
            else -> {}
        }
    }
}