package comeayoua.growthspace.datastore

import comeayoua.growthspace.model.VersionList
import kotlinx.coroutines.flow.Flow

interface VersionListStore {
    suspend fun getVersionList(): VersionList
    suspend fun updateVersionList(update: (VersionList) -> VersionList)
}