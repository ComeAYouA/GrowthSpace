package comeayoua.growthspace

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import comeayoua.growthspace.database.ProjectsDao
import comeayoua.growthspace.network.ProjectsApi
import comeayoua.growthspace.sync.worker.AddProjectSync
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GrowthSpaceApp : Application(), Configuration.Provider{
    @Inject
    lateinit var syncWorkManagerFactory: SyncWorkManagerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(syncWorkManagerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        WorkManager.initialize(this, workManagerConfiguration)
    }
}

class SyncWorkManagerFactory @Inject constructor(
    private val projectsDao: ProjectsDao,
    private val projectsApi: ProjectsApi
): WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        Log.d("myTag", "factory: $workerClassName")
        return when(workerClassName){
            AddProjectSync::class.java.name -> {
                AddProjectSync(
                    projectsApi = projectsApi,
                    projectsDao = projectsDao,
                    context = appContext,
                    workerParams = workerParameters
                )
            }

            else -> null
        }
    }
}


