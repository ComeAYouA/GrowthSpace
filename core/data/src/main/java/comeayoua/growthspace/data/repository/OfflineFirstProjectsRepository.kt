package comeayoua.growthspace.data.repository

import comeayoua.growthspace.data.ProjectsRepository
import comeayoua.growthspace.database.ProjectsDao
import comeayoua.growthspace.database.model.ProjectEntity
import comeayoua.growthspace.database.model.asEntity
import comeayoua.growthspace.database.model.asExternalModel
import comeayoua.growthspace.model.Project
import comeayoua.growthspace.sync.ProjectSyncManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstProjectsRepository @Inject constructor(
    private val projectsDataBase: ProjectsDao,
    private val projectSyncManager: ProjectSyncManager
): ProjectsRepository {
    override fun getProjects(): Flow<List<Project>> {
        return projectsDataBase.getProjects().map { projects ->
            projects.map(ProjectEntity::asExternalModel)
        }
    }

    override fun getProjectsByIds(ids: List<Int>): Flow<List<Project>> {
        return projectsDataBase.getProjectsByIds(ids).map { projects ->
            projects.map(ProjectEntity::asExternalModel)
        }
    }

    override fun getProject(id: Int): Flow<Project> {
        return projectsDataBase.getProject(id).map { project ->
            project?.asExternalModel()?:throw Exception("There is no projects with given id")
        }
    }

    override suspend fun addProject(project: Project) {
        project.asEntity().let { entity ->
            projectsDataBase.addProject(entity)

            projectSyncManager.enqueueAddProjectsSync(listOf(entity.key))
        }
    }

    override suspend fun updateProject(project: Project) {
        projectsDataBase.updateProject(project.asEntity())
    }
}