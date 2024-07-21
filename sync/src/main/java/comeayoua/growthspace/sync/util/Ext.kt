package comeayoua.growthspace.sync.util

import androidx.work.WorkInfo
fun List<WorkInfo>.anyRunning() = any { it.state == WorkInfo.State.RUNNING }