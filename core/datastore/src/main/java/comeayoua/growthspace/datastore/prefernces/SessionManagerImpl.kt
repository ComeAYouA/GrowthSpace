package comeayoua.growthspace.datastore.prefernces

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import comeayoua.growthspace.datastore.di.UserPreferencesDataStore
import io.github.jan.supabase.gotrue.SessionManager
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

internal const val SESSION_KEY = "session"
@Singleton
class SessionManagerImpl @Inject constructor(
    @UserPreferencesDataStore private val dataStore: DataStore<Preferences>
): SessionManager {
    override suspend fun deleteSession() {
        val sessionKey = stringPreferencesKey(USER_TOKEN_KEY)

        dataStore.edit {
           it.remove(sessionKey)
       }
    }

    override suspend fun loadSession(): UserSession? {
        val sessionKey = stringPreferencesKey(USER_TOKEN_KEY)

        val session = dataStore.data.first()[sessionKey] ?: return null

        return Json.decodeFromString<UserSession>(session)
    }

    override suspend fun saveSession(session: UserSession) {
        val sessionKey = stringPreferencesKey(USER_TOKEN_KEY)
        val sessionJson = Json.encodeToString(session)

        dataStore.edit {
            it[sessionKey] = sessionJson
        }
    }
}