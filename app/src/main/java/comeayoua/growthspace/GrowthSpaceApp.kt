package comeayoua.growthspace

import android.app.Application
import android.util.Log
import comeayoua.growthspace.sync.Sync
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GrowthSpaceApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Sync.initialize(this)
    }
}

