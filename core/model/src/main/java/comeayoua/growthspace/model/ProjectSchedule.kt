package comeayoua.growthspace.model

import kotlinx.serialization.Serializable

@Serializable
data class ProjectSchedule(
    val monday: Boolean = false,
    val tuesday: Boolean = false,
    val wednesday: Boolean = false,
    val thursday: Boolean = false,
    val friday: Boolean = false,
    val saturday: Boolean = false,
    val sunday: Boolean = false,
)