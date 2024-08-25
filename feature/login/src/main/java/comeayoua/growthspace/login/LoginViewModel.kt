package comeayoua.growthspace.login

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import comeayoua.growthspace.domain.login.SignInWithEmailUseCase
import comeayoua.growthspace.domain.login.SignInWithGoogleUseCase
import comeayoua.growthspace.domain.login.SignUpWithEmailUseCase
import comeayoua.growthspace.login.utils.handleLoginException
import comeayoua.growthspace.login.utils.rawNonceToGoogleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<LoginScreenState> = MutableStateFlow(LoginScreenState.Enabled)
    val uiState = _uiState.asStateFlow()

    fun signInWithGoogle(context: Context, onSignIn: () -> Unit) =
        viewModelScope.launch {
            _uiState.value = LoginScreenState.SyncingWithGoogle

            val rawNonce = UUID.randomUUID().toString()
            val googleOption = rawNonceToGoogleOptions(rawNonce)

            val credentialManager = CredentialManager.create(context)
            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleOption)
                .build()

            val signInResult: Result<Boolean> = try {
                val credentialManagerResult = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                val credential = credentialManagerResult.credential
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                signInWithGoogleUseCase(
                    googleIdToken = googleIdToken,
                    rawNonce = rawNonce
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                handleLoginException(e)
            }

            signInResult.getOrNull()?.let { success ->
                _uiState.value = LoginScreenState.Enabled

                if (success) {
                    _uiState.value = LoginScreenState.Success
                    delay(1000)
                    onSignIn.invoke()
                }
            } ?: run {
                _uiState.value = LoginScreenState.Error(
                    signInResult.exceptionOrNull()?.message ?: "Unknown Error"
                )
            }
        }

    fun signIn(email: String, password: String, onSignIn: () -> Unit) =
        viewModelScope.launch {
            _uiState.value = LoginScreenState.Loading

            val signInResult: Result<Boolean> = try {
                signInWithEmailUseCase(email, password)
            } catch (e: Exception) {
                Result.failure(e)
            }

            signInResult.getOrNull()?.let { success ->
                _uiState.value = LoginScreenState.Enabled

                if (success) {
                    _uiState.value = LoginScreenState.Success
                    delay(1000)
                    onSignIn.invoke()
                }
            } ?: run {
                _uiState.value = LoginScreenState.Error(
                    signInResult.exceptionOrNull()?.message ?: "Unknown Error"
                )
            }
        }


    suspend fun signUp(email: String, password: String, confirmPassword: String, onSignUp: () -> Unit) =
        viewModelScope.launch {
            _uiState.value = LoginScreenState.Loading

            if (confirmPassword != password) {
                _uiState.value = LoginScreenState.Error("Passwords are not the same")

                return@launch
            }

            val signUpResult: Result<Boolean> = try {
                signUpWithEmailUseCase(email, password)
            } catch (e: Exception) {
                Result.failure(e)
            }

            signUpResult.getOrNull()?.let { success ->
                _uiState.value = LoginScreenState.Enabled

                if (success) {
                    _uiState.value = LoginScreenState.Success
                    delay(1000)
                    onSignUp.invoke()
                }
            } ?: run {
                _uiState.value = LoginScreenState.Error(
                    signUpResult.exceptionOrNull()?.message ?: "Unknown Error"
                )
            }

        }

    fun dismissError(){
        _uiState.value = LoginScreenState.Enabled
    }
}