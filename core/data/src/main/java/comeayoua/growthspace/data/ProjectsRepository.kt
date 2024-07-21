package comeayoua.growthspace.data

import comeayoua.growthspace.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectsRepository {
    fun getProjects(): Flow<List<Project>>
    fun getProjectsByIds(ids: List<Int>): Flow<List<Project>>
    fun getProject(id: Int): Flow<Project>
    suspend fun addProject(project: Project)
    suspend fun updateProject(project: Project)
}