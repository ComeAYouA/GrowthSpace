package comeayoua.growthspace.database

import androidx.room.TypeConverter
import comeayoua.growthspace.model.ProjectType
import comeayoua.growthspace.model.Schedule
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID


internal class ProjectsTypeConverter {
    @TypeConverter
    fun fromBooleanListToJson(list: List<Boolean>) = Json.encodeToString(list)
    @TypeConverter
    fun fromJsonToBooleanList(json: String) = Json.decodeFromString<List<Boolean>>(json)
    @TypeConverter
    fun fromProjectTypeToInt(projectType: ProjectType) = projectType.ordinal
    @TypeConverter
    fun fromIntToProjectType(int: Int)
        = when(int){
            0 -> ProjectType.Project
            else -> ProjectType.Habit
        }
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(value) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }
    @TypeConverter
    fun uuidToString(uuid: UUID?): String?{
        return uuid?.toString()
    }
    @TypeConverter
    fun fromStringToUUID(value: String?): UUID?{
        return UUID.fromString(value)
    }
    @TypeConverter
    fun fromProjectScheduleToString(schedule: Schedule) = Json.encodeToString(schedule)
    @TypeConverter
    fun fromJsonToProjectSchedule(json: String) = Json.decodeFromString<Schedule>(json)
}