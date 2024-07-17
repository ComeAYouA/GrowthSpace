package comeayoua.growthspace.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import comeayoua.growthspace.datastore.di.UserPreferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal const val USER_TOKEN_KEY = "user_token"
internal const val ONBOARDING_STATUS_KEY = "onboarding_status"

internal class UserDataStoreImpl @Inject constructor(
    @UserPreferencesDataStore private val dataStore: DataStore<Preferences>
): UserDataStore {
    override suspend fun saveToken(token: String) {
        val userTokenKey = stringPreferencesKey(USER_TOKEN_KEY)
        dataStore.edit { preferences ->
            preferences[userTokenKey] = token
        }
    }

    override fun getToken(): Flow<String?> {
        val userTokenKey = stringPreferencesKey(USER_TOKEN_KEY)
        return dataStore.data.map { preferences ->
            preferences[userTokenKey]
        }
    }

    override suspend fun saveOnBoardingStatus(onBoarded: Boolean) {
        val onBoardingStatusKey = booleanPreferencesKey(ONBOARDING_STATUS_KEY)
        dataStore.edit { preferences ->
            preferences[onBoardingStatusKey] = onBoarded
        }
    }

    override fun getOnBoardingStatus(): Flow<Boolean> {
        val onBoardingStatusKey = booleanPreferencesKey(ONBOARDING_STATUS_KEY)
        return dataStore.data.map { preferences ->
            preferences[onBoardingStatusKey] ?: false
        }
    }
}