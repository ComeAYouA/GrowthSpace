package comeayoua.growthspace.util

import comeayoua.growthspace.model.ProjectSchedule
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

object ProjectScheduleSerializer : KSerializer<ProjectSchedule> {
    override val descriptor = PrimitiveSerialDescriptor("ProjectSchedule", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ProjectSchedule {
        val input = decoder.decodeString()
        return Json.decodeFromString<ProjectSchedule>(input)
    }

    override fun serialize(encoder: Encoder, value: ProjectSchedule) {
        encoder.encodeString(Json.encodeToString(value))
    }
}