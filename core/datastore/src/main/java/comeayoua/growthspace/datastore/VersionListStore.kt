package comeayoua.growthspace.datastore

import comeayoua.growthspace.model.VersionList

interface VersionListStore {
    suspend fun getVersionList(): VersionList
    suspend fun updateVersionList(update: (VersionList) -> VersionList)
}