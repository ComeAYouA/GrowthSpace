package comeayoua.growthspace.sync

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import comeayoua.growthspace.sync.manager.PROJECTS_SYNC_WORK_NAME
import comeayoua.growthspace.sync.worker.AddProjectSync

object ProjectSync {

    fun initialize(context: Context) {
        WorkManager.getInstance(context).apply {
            enqueueUniqueWork(
                PROJECTS_SYNC_WORK_NAME,
                ExistingWorkPolicy.KEEP,
                AddProjectSync.syncProjectsInsert(listOf()),
            )
        }
    }
}
