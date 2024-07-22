package comeayoua.growthspace.sync.worker.project

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import comeayoua.growthspace.database.ProjectsDao
import comeayoua.growthspace.database.model.asExternalModel
import comeayoua.growthspace.network.ProjectsApi
import comeayoua.growthspace.network.model.toNetworkResource
import comeayoua.growthspace.sync.util.networkConnectionConstraints
import comeayoua.growthspace.sync.worker.DelegatingWorker
import comeayoua.growthspace.sync.worker.inputData
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.UUID

@HiltWorker
class AddProjectSync @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val projectsDao: ProjectsDao,
    private val projectsApi: ProjectsApi
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val insertedEntitiesKeys = inputData.getStringArray(SYNC_IDS_KEY)?.map { key ->
                UUID.fromString(key)
            }
            Log.d("myTag", "$insertedEntitiesKeys")


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
            Result.success()
        }catch (e: Exception){
            Log.d("myTag", "$e")
            Result.retry( )
        }
    }

    companion object {
        private const val SYNC_IDS_KEY = "SyncIdsKey"

        fun syncProjectsInsert(keys: List<UUID>?): OneTimeWorkRequest {
            val inputData = AddProjectSync::class.inputData()
                .apply {
                    if (keys != null) putStringArray(
                        SYNC_IDS_KEY,
                        keys.map { it.toString() }
                            .toTypedArray()
                    )
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