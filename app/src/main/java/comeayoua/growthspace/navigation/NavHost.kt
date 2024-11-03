package comeayoua.growthspace.navigation

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import comeayoua.growthspace.login.navigation.loginScreen
import comeayoua.growthspace.login.navigation.navigateToLoginScreen
import comeayoua.growthspace.onboarding.navigation.onBoardingScreen
import comeayoua.growthspace.projectadd.navigation.addProjectScreen
import comeayoua.growthspace.projectadd.navigation.navigateToAddProjectScreen
import comeayoua.growthspace.projects.navigation.navigateToProjectsListScreen
import comeayoua.growthspace.projects.navigation.projectsListScreen

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
        startDestination = startDestination,
        enterTransition = {
            scaleIn(initialScale = 0.92f)
        },
        exitTransition = {
            scaleOut(targetScale = 0.92f)
        }
    ){
        loginScreen(
            onLogin = {
                navController.navigateToProjectsListScreen {
                    popUpTo(0)
                }
            },
        )
        projectsListScreen(
            onCreateProject = {
                navController.navigateToAddProjectScreen()
            }
        )
        addProjectScreen(
            onAddProject = {
                navController.navigateUp()
            }
        )
        onBoardingScreen(
            onStartButtonClicked = { navController.navigateToLoginScreen()}
        )
    }
}
