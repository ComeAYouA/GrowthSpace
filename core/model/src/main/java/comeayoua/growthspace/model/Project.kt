package comeayoua.growthspace.model

import kotlinx.datetime.LocalDateTime
import java.util.UUID

data class Project(
    val id: Int,
    val name: String,
    val description: String,
    val isPublic: Boolean,
    val projectType: ProjectType,
    val createdAt: LocalDateTime,
    val progress: Int,
    val ownerId: UUID,
    val streak: Int,
    val projectSchedule: ProjectSchedule
)