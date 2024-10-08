package comeayoua.growthspace.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import comeayoua.growthspace.database.model.ProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectsDao {
    @Query("SELECT * FROM projectentity")
    fun getProjects(): Flow<List<ProjectEntity>>
    @Query("SELECT * FROM projectentity WHERE id =(:id)")
    fun getProject(id: Int): Flow<ProjectEntity?>
    @Query("SELECT * FROM projectentity WHERE isSynced = 0")
    suspend fun getProjectsUpdates(): List<ProjectEntity>
    @Upsert()
    suspend fun updateProject(projects: ProjectEntity)
    @Insert
    suspend fun addProject(project: ProjectEntity)
    @Delete
    suspend fun deleteProject(project: ProjectEntity)
}