package comeayoua.growthspace.sync

import android.content.Context
import androidx.work.WorkManager

object Sync {
    fun initialize(context: Context) {
        WorkManager.getInstance(
            context
        )
    }
}
