package comeayoua.growthspace.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import comeayoua.growthspace.model.Project
import comeayoua.growthspace.model.ProjectType
import kotlinx.datetime.LocalDateTime
import java.util.Date
import java.util.UUID

@Entity
data class ProjectEntity(
    var id: Int,
    val name: String,
    val description: String,
    val isPublic: Boolean,
    val projectType: ProjectType,
    val createdAt: LocalDateTime,
    val progress: Int,
    val ownerId: UUID,
    val streak: Int,
    val daysOfWeek: List<Boolean>
){
    @PrimaryKey(autoGenerate = true)
    var key: Int = 0
}

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
        daysOfWeek = this.daysOfWeek
    )

fun Project.toInternalModel(): ProjectEntity
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
        daysOfWeek = this.daysOfWeek
    )