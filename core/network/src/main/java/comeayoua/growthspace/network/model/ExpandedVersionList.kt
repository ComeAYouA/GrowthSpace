package comeayoua.growthspace.network.model

import comeayoua.growthspace.util.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ExpandedVersionList(
    @SerialName("id")
    val id: Int,
    @SerialName("user_id")
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    @SerialName("projects_version")
    val projectsVersion: Int
)