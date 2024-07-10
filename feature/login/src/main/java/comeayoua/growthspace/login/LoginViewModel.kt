package comeayoua.growthspace.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import comeayoua.growthspace.domain.SignInWithGoogleUseCase
import comeayoua.growthspace.login.ui.stateholders.FormState
import comeayoua.growthspace.login.utils.rawNonceToGoogleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.Normalizer.Form
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: Auth,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase
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

        return try {
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

            _uiState.update { LoginScreenState.Enabled }
            true
        }catch (e: NoCredentialException){
            Toast.makeText(
                context,
                "Google credentials are missing",
                Toast.LENGTH_LONG
            ).show()
            _uiState.update { LoginScreenState.Enabled }
            false
        }catch (e: GetCredentialCancellationException){
            _uiState.update { LoginScreenState.Enabled }
            false
        }catch (e: CreateCredentialCancellationException){
            false
        }catch (e: Exception){
            Toast.makeText(
                context,
                "Unknown error. Please try again later/",
                Toast.LENGTH_LONG
            ).show()
            _uiState.update { LoginScreenState.Enabled }
            false
        }
    }

    suspend fun signIn(email: String, password: String): Boolean{
        _uiState.value = LoginScreenState.LoginningUp

        val correct = try {
            auth.signInWith(Email){
                this.email = email
                this.password = password
            }

            Log.d("myTag", auth.currentUserOrNull().toString())
            _formState.value = FormState.Valid
            auth.currentUserOrNull() != null
        } catch (e: RestException) {
            _formState.value = FormState.Error(e.description?:"")

            false
        }

        _uiState.value = LoginScreenState.Enabled

        return correct
    }

    suspend fun signUp(email: String, password: String): Boolean {
        _uiState.value = LoginScreenState.SigningUp

        val correct =  try {
            auth.signUpWith(Email){
                this.email = email
                this.password = password
            }

            Log.d("myTag", auth.currentUserOrNull().toString())
            _formState.value = FormState.Valid
            auth.currentUserOrNull() != null
        } catch (e: RestException) {
            _formState.value = FormState.Error(e.description?:e.message?.lines()?.first()?:"")
            false
        }

        _uiState.value = LoginScreenState.Enabled

        return correct
    }

    fun updateFormState(state: FormState){
        _formState.value = state
    }
}