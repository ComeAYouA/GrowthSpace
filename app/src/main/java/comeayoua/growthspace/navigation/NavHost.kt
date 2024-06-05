package comeayoua.growthspace.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import comeayoua.growthspace.login.navigation.loginScreen
import comeayoua.growthspace.login.navigation.navigateToLoginScreen
import comeayoua.growthspace.onboarding.navigation.onBoardingScreen
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
        onBoardingScreen(
            onStartButtonClicked = { navController.navigateToLoginScreen()}
        )
    }
}
