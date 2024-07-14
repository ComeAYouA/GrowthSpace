package comeayoua.growthspace.domain

import comeayoua.growthspace.auth.util.UserDataUtil
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val auth: Auth,
    private val userDataUtil: UserDataUtil
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

    private fun saveToken(){
        val token = auth.currentAccessTokenOrNull()

        userDataUtil.saveUserToken(token)
    }
}