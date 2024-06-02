//package comeayoua.growthspace.login
//
//import android.util.Log
//import androidx.compose.runtime.collectAsState
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import comeayoua.growthspace.auth.UserDataRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import io.github.jan.supabase.compose.auth.ComposeAuth
//import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
//import io.github.jan.supabase.gotrue.Auth
//import io.github.jan.supabase.gotrue.SessionStatus
//import io.github.jan.supabase.gotrue.providers.Google
//import kotlinx.coroutines.async
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class LoginViewModel @Inject constructor(
//    val composeAuth: ComposeAuth,
//    private val auth: Auth,
//    private val userDataRepository: UserDataRepository
//): ViewModel() {
//    private val _uiState: MutableStateFlow<LoginScreenState> = MutableStateFlow(LoginScreenState.Loading)
//    val uiState = _uiState.asStateFlow()
//
//    init {
//        checkLoggStatus()
//    }
//
//
//    private suspend fun saveToken(){
//        val token = auth.currentAccessTokenOrNull()
//
//        userDataRepository.saveLoginToken(token)
//    }
//
//    private suspend fun getToken(): String? = userDataRepository.getLoginToken()
//
//    fun NativeSignInResult.retrieveGoogleAuth(){
//        when(this){
//            NativeSignInResult.Success -> {
//                viewModelScope.launch {
//                    _uiState.update {
//                        LoginScreenState.Loading
//                    }
//
//                    saveToken()
//
//                    _uiState.update {
//                        LoginScreenState.LoggedIn
//                    }
//                }
//            }
//            else -> {}
//        }
//    }
//
//    private fun checkLoggStatus(){
//        viewModelScope.launch {
//            try {
//                val token = getToken()
//
//                if(token.isNullOrEmpty()) {
//
//                } else {
//                    auth.retrieveUser(token)
//                    auth.refreshCurrentSession()
//                    saveToken()
//                    _uiState.update {
//                        LoginScreenState.LoggedIn
//                    }
//                }
//            } catch (e: Exception) {
//
//            }
//        }
//    }
//}