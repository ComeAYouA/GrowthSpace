package comeayoua.growthspace.datastore.prefernces

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import comeayoua.growthspace.datastore.VersionListStore
import comeayoua.growthspace.datastore.di.UserPreferencesDataStore
import comeayoua.growthspace.model.VersionList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

internal const val VERSION_LIST_KEY = "version_list"

@Singleton
internal class VersionListStoreImpl @Inject constructor(
    @UserPreferencesDataStore private val dataStore: DataStore<Preferences>
): VersionListStore {
    override suspend fun getVersionList(): VersionList {
        val versionListKey = stringPreferencesKey(VERSION_LIST_KEY)

        val versionList = VersionList(-1)

        return if (versionList == null) {
            val newVersionList = VersionList()
            updateVersionList { newVersionList }
            newVersionList
        } else {
            versionList
        }
    }

    override suspend fun updateVersionList(update: (VersionList) -> VersionList) {
        val versionListKey = stringPreferencesKey(VERSION_LIST_KEY)
        dataStore.edit { preferences ->
            val versionList = preferences[versionListKey]?.let { versionList ->
                Json.decodeFromString<VersionList>(versionList)
            } ?: VersionList()
            val updatedVersionList = update(versionList)
            preferences[versionListKey] = Json.encodeToString(updatedVersionList)
        }
    }
}