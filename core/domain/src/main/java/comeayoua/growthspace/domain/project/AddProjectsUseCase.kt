package comeayoua.growthspace.domain.project

import comeayoua.growthspace.data.ProjectsRepository
import comeayoua.growthspace.model.Project
import javax.inject.Inject

class AddProjectsUseCase @Inject constructor(
    private val projectsRepository: ProjectsRepository
) {
    suspend operator fun invoke(projects: List<Project>) =
        projectsRepository.addProjects(projects)
}