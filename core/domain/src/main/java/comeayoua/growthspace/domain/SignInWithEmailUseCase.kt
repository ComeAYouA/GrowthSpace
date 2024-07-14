package comeayoua.growthspace.domain

import comeayoua.growthspace.auth.util.UserDataUtil
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val auth: Auth,
    private val userDataUtil: UserDataUtil
){
    suspend operator fun invoke(email: String, password: String): Boolean{
        auth.signInWith(Email) {
            this.email = email
            this.password = password
        }

        saveToken()

        return auth.currentUserOrNull() != null
    }

    private fun saveToken(){
        val token = auth.currentAccessTokenOrNull()

        userDataUtil.saveUserToken(token)
    }
}