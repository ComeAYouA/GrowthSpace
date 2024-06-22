package comeayoua.growthspace.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import comeayoua.growthspace.model.UserData


@Composable
fun rememberAppUiState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
    userData: UserData
): AppUiState {
    return remember(
        windowSizeClass
    ){
        AppUiState(
            windowSizeClass,
            navController,
            userData
        )
    }
}

@Stable
class AppUiState(
    private val windowSizeClass: WindowSizeClass,
    val navController: NavHostController,
    private val userData: UserData,
) {
    val startDestination: String
    get() = if (userData.isUserLoggedIn) {
        "PROJECTS_ROUTE"
    } else {
        if (userData.isOnboarded){
            "LOGIN_ROUTE"
        } else {
            "ONBOARDING_ROUTE"
        }
    }

    val shouldShowBottomNavigationBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavigationRail: Boolean
        get() = !shouldShowBottomNavigationBar
}