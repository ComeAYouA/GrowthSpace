package comeayoua.growthspace.data

import kotlinx.coroutines.flow.Flow

interface UserDataRepository{
    suspend fun saveToken(token: String)
    fun getToken(): Flow<String?>
    suspend fun saveOnBoardingStatus(onBoarded: Boolean)
    fun getOnBoardingStatus(): Flow<Boolean>
}