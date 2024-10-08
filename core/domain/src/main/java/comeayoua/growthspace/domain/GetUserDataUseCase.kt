package comeayoua.growthspace.domain

import android.util.Log
import comeayoua.growthspace.data.UserDataRepository
import comeayoua.growthspace.model.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
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
            Log.d("myTag", token.toString())
            val isLoggedIn = token != null

            UserData(
                isUserLoggedIn = isLoggedIn,
                isOnboarded = isOnBoarded
            )
        }.flowOn(Dispatchers.IO)
    }
}