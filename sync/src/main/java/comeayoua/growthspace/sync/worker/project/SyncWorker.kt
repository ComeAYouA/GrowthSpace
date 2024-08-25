package comeayoua.growthspace.sync.worker.project

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import comeayoua.growthspace.data.ProjectsRepository
import comeayoua.growthspace.sync.util.networkConnectionConstraints
import comeayoua.growthspace.sync.worker.DelegatingWorker
import comeayoua.growthspace.sync.worker.inputData
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val projectsRepository: ProjectsRepository,
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        try {
            Log.d("myTag", "start work")

            val success = projectsRepository.syncData()
            if (success) Result.success() else Result.retry()

        }catch (e: Exception){
            Log.d("myTag", "$e")
            Result.retry()
        }
    }

    companion object {
        private const val SYNC_IDS_KEY = "SyncIdsKey"

        fun sync(): OneTimeWorkRequest {
            val inputData = SyncWorker::class.inputData()
                .build()

            return OneTimeWorkRequestBuilder<DelegatingWorker>()
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(networkConnectionConstraints)
                .setInputData(inputData)
                .build()
        }
    }
}