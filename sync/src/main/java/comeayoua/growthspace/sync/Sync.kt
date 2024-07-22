package comeayoua.growthspace.sync

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager

object Sync {
    fun initialize(context: Context, configuration: Configuration) {
        WorkManager.initialize(
            context,
            configuration
        )
    }
}
