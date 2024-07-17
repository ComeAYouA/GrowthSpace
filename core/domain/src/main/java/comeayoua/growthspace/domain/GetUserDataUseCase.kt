package comeayoua.growthspace.domain

import comeayoua.growthspace.data.UserDataRepository
import comeayoua.growthspace.model.UserData
import io.github.jan.supabase.gotrue.Auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository
){
    operator fun invoke(): Flow<UserData> {
        val getTokenFlow = userDataRepository.getToken()
        val onBoardingFlow = userDataRepository.getOnBoardingStatus()

        return combine(
            getTokenFlow,
            onBoardingFlow
        ){ token, isOnBoarded ->
            val isLoggedIn = token != null

            UserData(
                isUserLoggedIn = isLoggedIn,
                isOnboarded = isOnBoarded
            )
        }
    }
}