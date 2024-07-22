package comeayoua.growthspace.network.api

import comeayoua.growthspace.network.ProjectsApi
import comeayoua.growthspace.network.model.ProjectNetwork
import comeayoua.growthspace.network.model.ProjectNetworkExpanded
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.from
import java.util.UUID
import javax.inject.Inject

class ProjectsApiImpl @Inject constructor(
    private val supabase: SupabaseClient,
    private val auth: Auth
): ProjectsApi {
    override suspend fun getProject(): List<ProjectNetworkExpanded>
        = supabase.from("projects").select().decodeList()

    override suspend fun insertProject(project: ProjectNetwork): ProjectNetworkExpanded {
        val userId = auth.currentUserOrNull()!!.id

        return supabase.from("projects")
            .insert(
                project.copy(ownerId = UUID.fromString(userId))
            ){ this.select() }
                .decodeList<ProjectNetworkExpanded>()
                .last()
    }
}