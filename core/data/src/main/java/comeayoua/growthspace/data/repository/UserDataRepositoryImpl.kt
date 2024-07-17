package comeayoua.growthspace.data.repository

import comeayoua.growthspace.data.UserDataRepository
import comeayoua.growthspace.datastore.UserDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class UserDataRepositoryImpl @Inject constructor(
    private val userDataStore: UserDataStore
): UserDataRepository {
    override suspend fun saveToken(token: String) = userDataStore.saveToken(token)
    override fun getToken(): Flow<String?> = userDataStore.getToken()
    override suspend fun saveOnBoardingStatus(onBoarded: Boolean) = userDataStore.saveOnBoardingStatus(onBoarded)
    override fun getOnBoardingStatus(): Flow<Boolean> = userDataStore.getOnBoardingStatus()
}