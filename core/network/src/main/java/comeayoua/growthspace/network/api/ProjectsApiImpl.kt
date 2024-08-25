package comeayoua.growthspace.network.api

import comeayoua.growthspace.network.ProjectsApi
import comeayoua.growthspace.network.model.ProjectNetwork
import comeayoua.growthspace.network.model.ProjectNetworkVersioned
import comeayoua.growthspace.network.model.VersionParameter
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.Auth
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
    override suspend fun getProjects(): List<ProjectNetwork>
        = supabase.from("projects").select().decodeList()

    override suspend fun getUpdates(
        version: Int
    ): List<ProjectNetworkVersioned> =
        withContext(Dispatchers.IO) {
            supabase.postgrest.rpc(
                "get_projects_updates",
                parameters = VersionParameter(version)
            ).decodeList()
        }


    override suspend fun insertProjects(
        projects: List<ProjectNetwork>,
        commitVersion: Int
    ) = withContext(Dispatchers.IO) {
        val userId = auth.currentUserOrNull()!!.id
        val userUUID = UUID.fromString(userId)

        val projectsToInsert =  projects.map { project -> project.copy(ownerId = userUUID) }

        val insertResult = supabase.from("projects")
            .insert(projectsToInsert) { this.select() }
            .decodeList<ProjectNetwork>()

        insertResult
    }

    override suspend fun updateProjects(
        projects: List<ProjectNetwork>,
        commitVersion: Int
    ) = withContext(Dispatchers.IO) {
        supabase.from("projects").upsert(
            projects,
            onConflict = "project_id"
        )

        Unit
    }
}