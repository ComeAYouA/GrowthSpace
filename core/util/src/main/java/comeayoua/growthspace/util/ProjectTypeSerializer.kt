package comeayoua.growthspace.util

import comeayoua.growthspace.model.ProjectType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.UUID

object ProjectTypeSerializer : KSerializer<ProjectType> {
    override val descriptor = PrimitiveSerialDescriptor("ProjectType", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ProjectType {
        val input = decoder.decodeString()
        return when(input){
            "project" -> ProjectType.Project
            else -> ProjectType.Habit
        }
    }

    override fun serialize(encoder: Encoder, value: ProjectType) {
        val output = when(value){
            ProjectType.Project -> "project"
            ProjectType.Habit -> "habit"
        }
        encoder.encodeString(output)
    }
}