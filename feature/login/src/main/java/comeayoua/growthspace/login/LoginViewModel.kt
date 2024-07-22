package comeayoua.growthspace.login

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import comeayoua.growthspace.domain.login.SignInWithEmailUseCase
import comeayoua.growthspace.domain.login.SignInWithGoogleUseCase
import comeayoua.growthspace.domain.login.SignUpWithEmailUseCase
import comeayoua.growthspace.login.ui.stateholders.FormState
import comeayoua.growthspace.login.utils.handleLoginException
import comeayoua.growthspace.login.utils.rawNonceToGoogleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private val _formState: MutableStateFlow<FormState> = MutableStateFlow(FormState.Valid)
    val formState = _formState.asStateFlow()

    suspend fun signInWithGoogle(context: Context): Boolean{
        _uiState.value = LoginScreenState.SyncingWithGoogle

        val rawNonce = UUID.randomUUID().toString()
        val googleOption = rawNonceToGoogleOptions(rawNonce)

        val credentialManager = CredentialManager.create(context)
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleOption)
            .build()

        val requestValidation =  try {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential
                .createFrom(credential.data)
            val googleIdToken = googleIdTokenCredential.idToken

            signInWithGoogleUseCase(
                googleIdToken = googleIdToken,
                rawNonce = rawNonce
            )

        }catch (e: Exception){
            val error = handleLoginException(e)
            error.message?.let{ message ->
                _formState.value = FormState.Error(message)
            }

            false
        }

        _uiState.update { LoginScreenState.Enabled }

        return requestValidation
    }

    suspend fun signIn(email: String, password: String): Boolean{
        _uiState.value = LoginScreenState.LoginningUp

        val correct = try {
            val request = signInWithEmailUseCase(email, password)

            _formState.value = FormState.Valid

            request
        } catch (e: Exception) {
            val error = handleLoginException(e)

            error.message?.let { message ->
                _formState.value = FormState.Error(message)
            }
            false
        }

        _uiState.value = LoginScreenState.Enabled

        return correct
    }

    suspend fun signUp(email: String, password: String, confirmPassword: String): Boolean {
        _uiState.value = LoginScreenState.SigningUp

        if (confirmPassword != password) {
            _formState.value = FormState.Error(message = "Passwords are not similar")
            _uiState.value = LoginScreenState.Enabled
            return false
        }

        val correct =  try {
            val request = signUpWithEmailUseCase(email, password)

            _formState.value = FormState.Valid

            request
        } catch (e: Exception) {
            val error = handleLoginException(e)
            error.message?.let { message ->
                _formState.value = FormState.Error(message)
            }

            false
        }

        _uiState.value = LoginScreenState.Enabled

        return correct
    }

    fun updateFormState(state: FormState){
        _formState.value = state
    }
}