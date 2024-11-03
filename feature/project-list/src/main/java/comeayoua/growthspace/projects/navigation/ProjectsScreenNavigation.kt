package comeayoua.growthspace.projects.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import comeayoua.growthspace.projects.ProjectsListScreen

const val PROJECTS_ROUTE = "projects_route"
private const val URI_PATTERN_LINK = "https://www.comayoua.growthspace/projects"

fun NavController.navigateToProjectsListScreen(navOptionsBuilder: NavOptionsBuilder.() -> Unit = {})
        = this.navigate(PROJECTS_ROUTE, navOptionsBuilder)

fun NavGraphBuilder.projectsListScreen(
    onCreateProject: () -> Unit
) {
    composable(
        route = PROJECTS_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern = URI_PATTERN_LINK },
        ),
    ) {
        ProjectsListScreen(
            onCreateProject = onCreateProject
        )
    }
}
