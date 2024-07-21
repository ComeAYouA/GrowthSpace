package comeayoua.growthspace.sync

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import comeayoua.growthspace.sync.source.PROJECTS_SYNC_WORK_NAME
import comeayoua.growthspace.sync.worker.AddProjectSync

object ProjectSync {
    // This method is initializes sync, the process that keeps the app's data current.
    // It is called from the app module's Application.onCreate() and should be only done once.
    fun initialize(context: Context) {
        WorkManager.getInstance(context).apply {
            // Run sync on app startup and ensure only one sync worker runs at any time
            enqueueUniqueWork(
                PROJECTS_SYNC_WORK_NAME,
                ExistingWorkPolicy.KEEP,
                AddProjectSync.syncProjectsInsert(listOf()),
            )
        }
    }
}
