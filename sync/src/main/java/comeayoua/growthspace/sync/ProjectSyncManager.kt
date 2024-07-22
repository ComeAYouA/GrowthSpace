package comeayoua.growthspace.sync

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ProjectSyncManager {
    val isSyncing: Flow<Boolean>
    fun enqueueAddProjectsSync(keys: List<UUID>)
}