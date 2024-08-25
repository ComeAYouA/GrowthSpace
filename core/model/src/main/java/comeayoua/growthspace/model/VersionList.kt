package comeayoua.growthspace.model

import kotlinx.serialization.Serializable

@Serializable
data class VersionList(
    val projectsListVersion: Int = 0
)