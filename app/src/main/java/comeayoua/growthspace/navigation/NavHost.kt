package comeayoua.growthspace.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import comeayoua.growthspace.login.navigation.loginScreen
import comeayoua.growthspace.projects.navigation.projectsScreen


@Composable
fun GrowthSpaceNavHost(
    modifier: Modifier = Modifier,
    appUiState: AppUiState,
    startDestination: String = appUiState.startDestination,
){
    val navController = appUiState.navController

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ){
        loginScreen()
        projectsScreen()
    }
}

val AppUiState.startDestination: String
    get() = if (this.isUserLoggedIn) "PROJECTS_ROUTE" else "LOGIN_ROUTE"