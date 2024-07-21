package comeayoua.growthspace.network

import comeayoua.growthspace.network.model.ProjectNetwork
import comeayoua.growthspace.network.model.ProjectNetworkExpanded

interface ProjectsApi {
    suspend fun getProject(): List<ProjectNetworkExpanded>
    suspend fun insertProject(project: ProjectNetwork): ProjectNetworkExpanded
}