package comeayoua.growthspace.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import comeayoua.growthspace.auth.util.UserDataUtil
import comeayoua.growthspace.feature.login.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: Auth,
    private val userDataUtil: UserDataUtil
): ViewModel() {
    private val _uiState: MutableStateFlow<LoginScreenState> = MutableStateFlow(LoginScreenState.Enabled)
    val uiState = _uiState.asStateFlow()

    private fun saveToken(){
        val token = auth.currentAccessTokenOrNull()
        Log.d("myTag", token.toString())
        userDataUtil.saveUserToken(token)
    }

    suspend fun signIn(context: Context): Boolean{
        _uiState.update { LoginScreenState.Disabled }

        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it)}

        val credentialManager = CredentialManager.create(context)

        val googleOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.clientId)
            .setNonce(hashedNonce)
            .build()

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

            auth.signUpWith(IDToken) {
                idToken = googleIdToken
                provider = Google
                nonce = rawNonce
            }

            saveToken()

            _uiState.update { LoginScreenState.Enabled }

            true
        }catch (e: CreateCredentialCancellationException){
            false
        }catch (e: Exception){
            _uiState.update { LoginScreenState.Enabled }
            Toast.makeText(context, "There was an error during registration, please try again", Toast.LENGTH_LONG).show()
            false
        }

    }
}