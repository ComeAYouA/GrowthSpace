package comeayoua.growthspace.domain.login

import comeayoua.growthspace.data.UserDataRepository
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val auth: Auth,
    private val userDataRepository: UserDataRepository
){
    suspend operator fun invoke(googleIdToken: String, rawNonce: String): Boolean {
        auth.signUpWith(IDToken) {
            idToken = googleIdToken
            provider = Google
            nonce = rawNonce
        }

        saveToken()

        return auth.currentUserOrNull() != null
    }

    private suspend fun saveToken(){
        val token = auth.currentAccessTokenOrNull()

        token?.let {
            userDataRepository.saveToken(it)
        }
    }
}