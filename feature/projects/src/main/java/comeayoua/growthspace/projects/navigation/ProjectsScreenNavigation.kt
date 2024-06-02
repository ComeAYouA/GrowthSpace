package comeayoua.growthspace.projects.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import comeayoua.growthspace.projects.Projects

const val PROJECTS_ROUTE = "projects_route"
private const val URI_PATTERN_LINK = "https://www.comayoua.growthspace/projects"

fun NavController.navigateToProjectsScreen(navOptions: NavOptions)
        = this.navigate(PROJECTS_ROUTE, navOptions)

fun NavGraphBuilder.projectsScreen() {
    composable(
        route = PROJECTS_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern = URI_PATTERN_LINK },
        ),
    ) {
        Projects()
    }
}
