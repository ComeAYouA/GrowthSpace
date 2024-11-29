package comeayoua.growthspace.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Example json:
 *
 *      "habitId": "123",
 *      "name": "Morning Jog",
 *      "schedule": {
 *          "type": "weekly",
 *          "details": {
 *              "days": ["1", "3", "5"]
 *          }
 *       }
 *  Days of month starts from 1
 *  Days of week starts from 1
 **/

@Serializable
data class Schedule(
    val type: ScheduleType,
    val value: ScheduleValue?
)

@Serializable
enum class ScheduleType {
    @SerialName("daily")
    DAILY,
    @SerialName("weekly")
    WEEKLY,
    @SerialName("monthly")
    MONTHLY,
    @SerialName("once")
    ONCE
}

@Serializable
data class ScheduleValue(val days: List<Int>)