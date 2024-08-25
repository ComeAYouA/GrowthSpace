package comeayoua.growthspace.database.room

import comeayoua.growthspace.database.ProjectsDao
import comeayoua.growthspace.database.model.ProjectEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectsDataBaseImpl @Inject constructor(
    private val dataBase: ProjectsDataBase
): ProjectsDao{

    private val dao = dataBase.projectsDao()
    override fun getProjects() = dao.getProjects()

    override fun getProject(id: Int) = dao.getProject(id)
    override suspend fun getProjectsUpdates(): List<ProjectEntity> = dao.getProjectsUpdates()

    override suspend fun updateProject(projects: ProjectEntity) = dao.updateProject(projects)

    override suspend fun addProject(project: ProjectEntity) = dao.addProject(project)
    override suspend fun deleteProject(project: ProjectEntity) = dao.deleteProject(project)
}