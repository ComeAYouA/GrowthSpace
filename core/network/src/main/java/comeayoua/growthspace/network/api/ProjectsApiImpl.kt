package comeayoua.growthspace.network.api

import comeayoua.growthspace.network.ProjectsApi
import comeayoua.growthspace.network.model.ExpandedVersionList
import comeayoua.growthspace.network.model.ProjectNetwork
import comeayoua.growthspace.network.model.ProjectNetworkVersioned
import comeayoua.growthspace.network.model.VersionParameter
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class ProjectsApiImpl @Inject constructor(
    private val supabase: SupabaseClient,
    private val auth: Auth,
): ProjectsApi {
    override suspend fun getProjects(): List<ProjectNetwork> {
        return supabase.from("projects").select().decodeList()
    }

    override suspend fun getUpdates(
        version: Int
    ): List<ProjectNetworkVersioned> {
        return withContext(Dispatchers.IO) {
            supabase.postgrest.rpc(
                "get_projects_updates",
                parameters = VersionParameter(version)
            ).decodeList()
        }
    }


    //TODO: Delete select after insert
    override suspend fun insertProjects(
        projects: List<ProjectNetwork>
    ): Int {
        return withContext(Dispatchers.IO) {
            val userId = auth.currentUserOrNull()!!.id
            val userUUID = UUID.fromString(userId)

            val projectsToInsert =  projects.map { project -> project.copy(ownerId = userUUID) }

            supabase.from("projects")
                .insert(projectsToInsert) { this.select() }
                .decodeList<ProjectNetwork>()

            getProjectsVersion()
        }
    }

    override suspend fun updateProjects(
        projects: List<ProjectNetwork>,
    ): Int {
        return withContext(Dispatchers.IO) {
            supabase.from("projects").upsert(
                projects
            )

            getProjectsVersion()
        }
    }

    //TODO: ifEmpty fallback
    override suspend fun getProjectsVersion(): Int {
        return withContext(Dispatchers.IO){
            supabase.from("user_version_list")
                .select().decodeAs<List<ExpandedVersionList>>().first().projectsVersion
        }
    }
}