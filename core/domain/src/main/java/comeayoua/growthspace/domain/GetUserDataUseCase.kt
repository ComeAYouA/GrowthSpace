package comeayoua.growthspace.domain

import android.util.Log
import comeayoua.growthspace.auth.util.UserDataUtil
import comeayoua.growthspace.model.UserData
import io.github.jan.supabase.gotrue.Auth
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val auth: Auth,
    private val userDataUtil: UserDataUtil
){
    suspend operator fun invoke(): UserData {
        val isUserLoggedIn = checkLogin()
        val isOnboarded = userDataUtil.getOnBoardingStatus()

        return UserData(
            isUserLoggedIn = isUserLoggedIn,
            isOnboarded = isOnboarded
        )
    }

    private suspend fun checkLogin(): Boolean =
        try {
            val token = userDataUtil.getUserToken()

            if(token.isNullOrEmpty()) {
                false
            } else {
                auth.retrieveUser(token)
                auth.refreshCurrentSession()
                userDataUtil.saveUserToken(token)
                true
            }
        } catch (e: Exception) {
            false
        }
}