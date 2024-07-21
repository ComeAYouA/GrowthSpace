package comeayoua.growthspace

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import comeayoua.growthspace.database.ProjectsDao
import comeayoua.growthspace.network.ProjectsApi
import comeayoua.growthspace.sync.ProjectSync
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GrowthSpaceApp() : Application(){
    override fun onCreate() {
        super.onCreate()
        ProjectSync.initialize(this)
    }
}


