package comeayoua.growthspace.data

import comeayoua.growthspace.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectsRepository: Syncable {
    fun getProjects(): Flow<List<Project>>
    fun getProject(id: Int): Flow<Project>
    suspend fun addProjects(projects: List<Project>)
    suspend fun updateProjects(projects: List<Project>)
}