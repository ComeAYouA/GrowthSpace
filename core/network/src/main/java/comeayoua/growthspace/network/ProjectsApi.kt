package comeayoua.growthspace.network

import comeayoua.growthspace.network.model.ProjectNetwork
import comeayoua.growthspace.network.model.ProjectNetworkVersioned

interface ProjectsApi {
    suspend fun getProjects(): List<ProjectNetwork>
    suspend fun getUpdates(version: Int): List<ProjectNetworkVersioned>
    suspend fun insertProjects(
        projects: List<ProjectNetwork>,
        commitVersion: Int
    ): List<ProjectNetwork>
    suspend fun updateProjects(projects: List<ProjectNetwork>, commitVersion: Int)
}