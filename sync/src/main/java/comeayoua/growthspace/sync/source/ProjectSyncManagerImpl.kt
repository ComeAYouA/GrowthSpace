package comeayoua.growthspace.sync.source

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import comeayoua.growthspace.sync.ProjectSyncManager
import comeayoua.growthspace.sync.util.anyRunning
import comeayoua.growthspace.sync.worker.AddProjectSync
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal const val PROJECTS_SYNC_WORK_NAME = "ProjectSyncWorkName"

class ProjectSyncManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
): ProjectSyncManager {
    override val isSyncing: Flow<Boolean> =
        WorkManager.getInstance(context).getWorkInfosForUniqueWorkFlow(PROJECTS_SYNC_WORK_NAME)
            .map(List<WorkInfo>::anyRunning)
            .conflate()

    override fun enqueueAddProjectSync(keys: List<Int>){
        val workManager = WorkManager.getInstance(context)

        workManager.enqueueUniqueWork(
            PROJECTS_SYNC_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            AddProjectSync.syncProjectsInsert(keys),
        )
    }
}
