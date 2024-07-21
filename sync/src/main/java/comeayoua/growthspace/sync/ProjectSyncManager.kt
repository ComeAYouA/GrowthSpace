package comeayoua.growthspace.sync

import kotlinx.coroutines.flow.Flow

interface ProjectSyncManager {
    val isSyncing: Flow<Boolean>
    fun enqueueAddProjectSync(keys: List<Int>)
}