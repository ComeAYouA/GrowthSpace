package comeayoua.growthspace.projectadd.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import comeayoua.growthspace.projectadd.AddProjectScreen

const val PROJECT_ADD_ROUTE = "project_add_route"
private const val URI_PATTERN_LINK = "https://www.comayoua.growthspace/project_add"

fun NavController.navigateToAddProjectScreen(navOptionsBuilder: NavOptionsBuilder.() -> Unit = {})
        = this.navigate(PROJECT_ADD_ROUTE, navOptionsBuilder)

fun NavGraphBuilder.addProjectScreen(
    onAddProject: () -> Unit
) {
    composable(
        route = PROJECT_ADD_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern = URI_PATTERN_LINK },
        ),
    ) {
        AddProjectScreen(
            onAddProject
        )
    }
}
