package comeayoua.growthspace.domain

import comeayoua.growthspace.data.UserDataRepository
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import javax.inject.Inject

class SignUpWithEmailUseCase @Inject constructor(
    private val auth: Auth,
    private val userDataRepository: UserDataRepository
){
    suspend operator fun invoke(email: String, password: String): Boolean{
        auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }

        saveToken()

        return auth.currentUserOrNull() != null
    }

    private suspend fun saveToken(){
        val token = auth.currentAccessTokenOrNull()

        token?.let { userDataRepository.saveToken(it) }
    }
}