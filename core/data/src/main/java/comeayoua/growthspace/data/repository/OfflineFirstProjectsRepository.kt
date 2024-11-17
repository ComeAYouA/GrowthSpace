package comeayoua.growthspace.data.repository

import comeayoua.growthspace.data.ProjectsRepository
import comeayoua.growthspace.database.ProjectsDao
import comeayoua.growthspace.database.model.ProjectEntity
import comeayoua.growthspace.database.model.asEntity
import comeayoua.growthspace.database.model.asExternalModel
import comeayoua.growthspace.datastore.VersionListStore
import comeayoua.growthspace.model.Project
import comeayoua.growthspace.network.ProjectsApi
import comeayoua.growthspace.network.model.asExternalModel
import comeayoua.growthspace.network.model.toExpandedNetworkResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Integer.max
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstProjectsRepository @Inject constructor(
    private val projectsDataBase: ProjectsDao,
    private val projectsApi: ProjectsApi,
    private val versionListStore: VersionListStore
): ProjectsRepository {
    override suspend fun syncData(): Boolean {
        return try {
            val version = versionListStore.getVersionList().projectsListVersion

            // fetching network updates
            val updates = projectsApi.getUpdates(version)
            var nextVersion = version

            // delete and upsert projects, calculate next version
            updates.forEach{ project ->
                nextVersion = max(nextVersion, project.changeVersion)
                when {
                    project.isDeleted -> projectsDataBase.deleteProject(
                        project
                            .asExternalModel()
                            .apply { isSynced = true }
                            .asEntity()

                    )
                    else -> projectsDataBase.updateProject(
                        project
                            .asExternalModel()
                            .apply { isSynced = true }
                            .asEntity()
                    )
                }
            }

            versionListStore.updateVersionList { versionList ->
                versionList.copy(projectsListVersion = nextVersion)
            }

            // fetching local updates
            val localUpdates = projectsDataBase.getProjectsUpdates()

            // update project in network
            nextVersion = projectsApi.updateProjects(
                localUpdates.map { project ->
                    project
                    .asExternalModel()
                    .toExpandedNetworkResource()
                }
            )

            // set sync flag in local database
            localUpdates.forEach { project ->
                projectsDataBase.updateProject(
                    project.copy(isSynced = true)
                )
            }

            versionListStore.updateVersionList { versionList ->
                versionList.copy(projectsListVersion = nextVersion)
            }

            true
        }catch (e: Exception){
            false
        }
    }
    override fun getProjects(): Flow<List<Project>> {
        return projectsDataBase.getProjects().map { projects ->
            projects.map(ProjectEntity::asExternalModel)
        }
    }

    override fun getProject(id: Int): Flow<Project> {
        return projectsDataBase.getProject(id).map { project ->
            project?.asExternalModel() ?: throw Exception("There is no projects with given id")
        }
    }

    override suspend fun addProjects(projects: List<Project>) {
       projects.forEach { project ->
           projectsDataBase.addProject(project.asEntity())
       }
    }

    override suspend fun updateProjects(projects: List<Project>) {
        projects.forEach { project ->
            projectsDataBase.updateProject(project.asEntity())
        }
    }
}