package comeayoua.growthspace.network.model

import comeayoua.growthspace.model.Project
import comeayoua.growthspace.model.ProjectType
import comeayoua.growthspace.util.ProjectTypeSerializer
import comeayoua.growthspace.util.UUIDSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ProjectNetwork(
    @SerialName("project_name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("is_public")
    val isPublic: Boolean,
    @SerialName("project_type")
    @Serializable(with = ProjectTypeSerializer::class)
    val projectType: ProjectType,
    @SerialName("created_at")
    val createdAt: LocalDateTime,
    @SerialName("progress")
    val progress: Int,
    @SerialName("owner_id")
    @Serializable(with = UUIDSerializer::class)
    val ownerId: UUID,
    @SerialName("streak")
    val streak: Int,
)

fun ProjectNetwork.asExternalModel(id: Int): Project =
    Project(
        id = id,
        name = name,
        description = description,
        isPublic = isPublic,
        projectType = projectType,
        createdAt = createdAt,
        progress = progress,
        ownerId = ownerId,
        streak = streak,
        daysOfWeek = listOf()
    )

fun Project.toNetworkResource(): ProjectNetwork =
    ProjectNetwork(
        name = name,
        description = description,
        isPublic = isPublic,
        projectType = projectType,
        createdAt = createdAt,
        progress = progress,
        ownerId = ownerId,
        streak = streak,
    )