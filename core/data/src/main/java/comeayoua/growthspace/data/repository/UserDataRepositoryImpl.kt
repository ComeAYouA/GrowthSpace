package comeayoua.growthspace.data.repository

import android.util.Log
import comeayoua.growthspace.data.UserDataRepository
import comeayoua.growthspace.datastore.UserDataStore
import comeayoua.growthspace.datastore.VersionListStore
import comeayoua.growthspace.model.VersionList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserDataRepositoryImpl @Inject constructor(
    private val userDataStore: UserDataStore,
    private val versionList: VersionListStore
): UserDataRepository {
    override suspend fun saveToken(token: String) = userDataStore.saveToken(token)
    override fun getToken(): Flow<String?> = userDataStore.getToken()
    override suspend fun saveOnBoardingStatus(onBoarded: Boolean) = userDataStore.saveOnBoardingStatus(onBoarded)
    override fun getOnBoardingStatus(): Flow<Boolean> = userDataStore.getOnBoardingStatus()
}