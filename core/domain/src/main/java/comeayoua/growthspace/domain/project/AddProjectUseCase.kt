package comeayoua.growthspace.domain.project

import comeayoua.growthspace.data.ProjectsRepository
import comeayoua.growthspace.model.Project
import javax.inject.Inject

class AddProjectUseCase @Inject constructor(
    private val projectsRepository: ProjectsRepository
) {
    suspend operator fun invoke(project: Project) =
        projectsRepository.addProject(project)
}