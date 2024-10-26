package comeayoua.growthspace.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionParameter(
    @SerialName("version")
    val version: Int
)