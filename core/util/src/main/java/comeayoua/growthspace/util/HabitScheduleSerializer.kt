package comeayoua.growthspace.util

import comeayoua.growthspace.model.HabitSchedule
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

object HabitScheduleSerializer : KSerializer<HabitSchedule> {
    override val descriptor = PrimitiveSerialDescriptor("ProjectSchedule", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): HabitSchedule {
        val input = decoder.decodeString()
        return Json.decodeFromString<HabitSchedule>(input)
    }

    override fun serialize(encoder: Encoder, value: HabitSchedule) {
        encoder.encodeString(Json.encodeToString(value))
    }
}