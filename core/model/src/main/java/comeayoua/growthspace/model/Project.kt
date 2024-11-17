package comeayoua.growthspace.model

import kotlinx.datetime.LocalDateTime
import java.util.UUID

data class Project(
    val id: UUID,
    val name: String,
    val description: String,
    val isPublic: Boolean,
    val createdAt: LocalDateTime,
    val progress: Int,
    val ownerId: UUID,
    val streak: Int,
    val repeat: Boolean,
    val remind: Boolean,
    val schedule: HabitSchedule,
    var isSynced: Boolean = false
)