package comeayoua.growthspace.sync.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import comeayoua.growthspace.database.ProjectsDao
import comeayoua.growthspace.database.model.asExternalModel
import comeayoua.growthspace.network.ProjectsApi
import comeayoua.growthspace.network.model.toNetworkResource
import comeayoua.growthspace.sync.util.networkConnectionConstraints
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.time.Duration

@HiltWorker
class AddProjectSync @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val projectsDao: ProjectsDao,
    private val projectsApi: ProjectsApi
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        Log.d("myTag", "syncing...")
        return try {
            val insertedEntitiesKeys = inputData.getIntArray(SYNC_IDS_KEY)?.toList()
            insertedEntitiesKeys?.let { keys ->
                val entities = projectsDao.getProjectsByKeys(keys).first()

                for(entity in entities){
                    val insertedProject = projectsApi.insertProject(
                        entity.asExternalModel().toNetworkResource()
                    )

                    entity.id = insertedProject.id

                    projectsDao.updateProject(entity)
                }
            }
            Log.d("myTag", "success")
            Result.success()
        }catch (e: Exception){
            Log.d("myTag", "error: $e")
            Result.retry()
        }
    }

    companion object {
        private const val SYNC_IDS_KEY = "SyncIdsKey"

        fun syncProjectsInsert(keys: List<Int>?): OneTimeWorkRequest {
            val inputData = AddProjectSync::class.inputData()
                .apply {
                    if (keys != null) putIntArray(SYNC_IDS_KEY, keys.toIntArray())
                }
                .build()

            Log.d("myTag", "oneTime worker")

            return OneTimeWorkRequestBuilder<DelegatingWorker>()
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(networkConnectionConstraints)
                .setInputData(inputData)
                .build()
        }
    }
}