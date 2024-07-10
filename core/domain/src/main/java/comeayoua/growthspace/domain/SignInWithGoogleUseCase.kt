package comeayoua.growthspace.domain

import comeayoua.growthspace.auth.util.UserDataUtil
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.gotrue.user.UserInfo
import javax.inject.Inject
import comeayoua.growthspace.model.Result

class SignInWithGoogleUseCase @Inject constructor(
    private val auth: Auth,
    private val userDataUtil: UserDataUtil
){
    suspend operator fun invoke(googleIdToken: String, rawNonce: String){
        auth.signUpWith(IDToken) {
            idToken = googleIdToken
            provider = Google
            nonce = rawNonce
        }

        saveToken()
    }

    private fun saveToken(){
        val token = auth.currentAccessTokenOrNull()

        userDataUtil.saveUserToken(token)
    }
}