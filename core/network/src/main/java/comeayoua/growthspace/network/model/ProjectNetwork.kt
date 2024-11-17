package comeayoua.growthspace.network.model

import comeayoua.growthspace.model.Project
import comeayoua.growthspace.model.HabitSchedule
import comeayoua.growthspace.util.HabitScheduleSerializer
import comeayoua.growthspace.util.UUIDSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ProjectNetwork(
    @SerialName("project_id")
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @SerialName("project_name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("is_public")
    val isPublic: Boolean,
    // TODO: Delete projectType from server
    @SerialName("created_at")
    val createdAt: LocalDateTime,
    @SerialName("progress")
    val progress: Int,
    @SerialName("owner_id")
    @Serializable(with = UUIDSerializer::class)
    val ownerId: UUID,
    @SerialName("streak")
    val streak: Int,
    // TODO: Add repeat field to server
    val repeat: Boolean,
    // TODO: Add remind field to server
    val remind: Boolean,
    @SerialName("project_schedule")
    @Serializable(with = HabitScheduleSerializer::class)
    val schedule: HabitSchedule
)


fun Project.toExpandedNetworkResource(): ProjectNetwork =
    ProjectNetwork(
        id = id,
        name = name,
        description = description,
        isPublic = isPublic,
        createdAt = createdAt,
        progress = progress,
        ownerId = ownerId,
        streak = streak,
        schedule = schedule,
        repeat = repeat,
        remind = remind
    )