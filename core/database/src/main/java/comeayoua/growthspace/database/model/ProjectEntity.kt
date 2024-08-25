package comeayoua.growthspace.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import comeayoua.growthspace.model.Project
import comeayoua.growthspace.model.ProjectSchedule
import comeayoua.growthspace.model.ProjectType
import kotlinx.datetime.LocalDateTime
import java.util.UUID

@Entity
data class ProjectEntity(
    @PrimaryKey
    var id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val isPublic: Boolean,
    val projectType: ProjectType,
    val createdAt: LocalDateTime,
    val progress: Int,
    val ownerId: UUID,
    val streak: Int,
    val daysOfWeek: ProjectSchedule,
    var isSynced: Boolean = false
)

fun ProjectEntity.asExternalModel(): Project
    = Project(
        id = this.id,
        name = this.name,
        description = this.description,
        isPublic = this.isPublic,
        projectType = this.projectType,
        createdAt = this.createdAt,
        progress = this.progress,
        ownerId = this.ownerId,
        streak = this.streak,
        projectSchedule = this.daysOfWeek,
        isSynced = isSynced
    )

fun Project.asEntity(): ProjectEntity
    = ProjectEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        isPublic = this.isPublic,
        projectType = this.projectType,
        createdAt = this.createdAt,
        progress = this.progress,
        ownerId = this.ownerId,
        streak = this.streak,
        daysOfWeek = this.projectSchedule,
        isSynced = this.isSynced
    )