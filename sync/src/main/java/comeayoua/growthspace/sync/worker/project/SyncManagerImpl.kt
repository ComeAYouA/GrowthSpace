package comeayoua.growthspace.sync.worker.project

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import comeayoua.growthspace.sync.SyncManager
import comeayoua.growthspace.sync.util.anyRunning
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal const val PROJECTS_SYNC_WORK_NAME = "ProjectSyncWorkName"

class SyncManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
): SyncManager {
    override val isSyncing: Flow<Boolean> =
        WorkManager.getInstance(context).getWorkInfosForUniqueWorkFlow(PROJECTS_SYNC_WORK_NAME)
            .map(List<WorkInfo>::anyRunning)
            .conflate()

    override fun enqueueSync(){
        val workManager = WorkManager.getInstance(context)

        workManager.enqueueUniqueWork(
            PROJECTS_SYNC_WORK_NAME,
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            SyncWorker.sync(),
        )
    }
}
