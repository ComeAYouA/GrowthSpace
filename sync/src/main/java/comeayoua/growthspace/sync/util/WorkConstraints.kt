package comeayoua.growthspace.sync.util

import androidx.work.Constraints
import androidx.work.NetworkType

val networkConnectionConstraints: Constraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()