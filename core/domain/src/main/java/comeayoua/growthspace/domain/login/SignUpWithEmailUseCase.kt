package comeayoua.growthspace.domain.login

import comeayoua.growthspace.data.UserDataRepository
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.ktor.client.plugins.HttpRequestTimeoutException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class SignUpWithEmailUseCase @Inject constructor(
    private val auth: Auth,
    private val userDataRepository: UserDataRepository
){
    suspend operator fun invoke(email: String, password: String): Result<Boolean>{
        try {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            val token =  auth.currentAccessTokenOrNull()

            return if (token != null) {
                userDataRepository.saveToken(token)
                Result.success(true)
            } else Result.failure(
                IllegalArgumentException("Sign in failed")
            )

        } catch (e: CancellationException){
            throw e
        } catch (e: HttpRequestTimeoutException){
            return Result.failure(Throwable("The server response timed out"))
        } catch (e: HttpRequestException){
            return Result.failure(Throwable("Invalid http request"))
        } catch (e: Exception){
            return Result.failure(Throwable(e.localizedMessage?:""))
        }
    }
}