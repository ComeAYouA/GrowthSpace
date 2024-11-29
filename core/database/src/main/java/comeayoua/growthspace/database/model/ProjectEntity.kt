package comeayoua.growthspace.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import comeayoua.growthspace.model.Project
import comeayoua.growthspace.model.Schedule
import kotlinx.datetime.LocalDateTime
import java.util.UUID

// TODO: Add tree skin field (in future)
@Entity
data class ProjectEntity(
    @PrimaryKey
    var id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val isPublic: Boolean,
    val createdAt: LocalDateTime,
    val progress: Int,
    val ownerId: UUID,
    val streak: Int,
    val repeat: Boolean,
    val remind: Boolean,
    val schedule: Schedule,
    var isSynced: Boolean = false
)

fun ProjectEntity.asExternalModel(): Project
    = Project(
        id = this.id,
        name = this.name,
        description = this.description,
        isPublic = this.isPublic,
        createdAt = this.createdAt,
        progress = this.progress,
        ownerId = this.ownerId,
        streak = this.streak,
        schedule = this.schedule,
        repeat = this.repeat,
        remind = this.remind,
        isSynced = isSynced
    )

fun Project.asEntity(): ProjectEntity
    = ProjectEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        isPublic = this.isPublic,
        createdAt = this.createdAt,
        progress = this.progress,
        ownerId = this.ownerId,
        streak = this.streak,
        schedule = this.schedule,
        repeat = this.repeat,
        remind = this.remind,
        isSynced = this.isSynced
    )