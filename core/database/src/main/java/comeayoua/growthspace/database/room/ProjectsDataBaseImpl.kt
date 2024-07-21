package comeayoua.growthspace.database.room

import android.util.Log
import comeayoua.growthspace.database.ProjectsDao
import comeayoua.growthspace.database.model.ProjectEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectsDataBaseImpl @Inject constructor(
    private val dataBase: ProjectsDataBase
): ProjectsDao{

    private val dao = dataBase.projectsDao()
    override fun getProjects() = dao.getProjects()

    override fun getProject(id: Int) = dao.getProject(id)

    override fun getProjectsByIds(ids: List<Int>) = dao.getProjectsByIds(ids)

    override fun getProjectsByKeys(keys: List<Int>) = dao.getProjectsByKeys(keys)

    override suspend fun updateProject(projects: ProjectEntity) = dao.updateProject(projects)

    override suspend fun addProject(project: ProjectEntity) = dao.addProject(project)
}