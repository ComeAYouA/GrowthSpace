package comeayoua.growthspace.data

interface Syncable {
    suspend fun syncData(): Boolean
}