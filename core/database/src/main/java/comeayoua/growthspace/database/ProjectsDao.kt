package comeayoua.growthspace.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import comeayoua.growthspace.database.model.ProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectsDao {
    @Query("SELECT * FROM projectentity")
    fun getProjects(): Flow<List<ProjectEntity>>
    @Query("SELECT * FROM projectentity WHERE id =(:id)")
    fun getProject(id: Int): Flow<ProjectEntity?>
    @Query("SELECT * FROM projectentity WHERE id IN (:ids)")
    fun getProjectsByIds(ids: List<Int>): Flow<List<ProjectEntity>>
    @Query("SELECT * FROM projectentity WHERE `key` IN (:keys)")
    fun getProjectsByKeys(keys: List<Int>): Flow<List<ProjectEntity>>
    @Update
    suspend fun updateProject(projects: ProjectEntity)
    @Insert
    suspend fun addProject(project: ProjectEntity)
}