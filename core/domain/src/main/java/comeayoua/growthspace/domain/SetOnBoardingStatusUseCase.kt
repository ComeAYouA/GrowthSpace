package comeayoua.growthspace.domain

import comeayoua.growthspace.data.UserDataRepository
import javax.inject.Inject

class SetOnBoardingStatusUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository
) {
    suspend operator fun invoke(isOnBoarded: Boolean) {
        userDataRepository.saveOnBoardingStatus(isOnBoarded)
    }
}