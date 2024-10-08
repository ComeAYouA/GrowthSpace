package comeayoua.growthspace.domain.project

import comeayoua.growthspace.data.ProjectsRepository
import comeayoua.growthspace.model.Project
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProjectsUseCase @Inject constructor(
    private val projectsRepository: ProjectsRepository
) {
    operator fun invoke(): Flow<List<Project>> =
        projectsRepository.getProjects()
}