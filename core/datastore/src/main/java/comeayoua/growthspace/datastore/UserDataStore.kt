package comeayoua.growthspace.datastore

import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    suspend fun saveToken(token: String)
    fun getToken(): Flow<String?>
    suspend fun saveOnBoardingStatus(onBoarded: Boolean)
    fun getOnBoardingStatus(): Flow<Boolean>
}