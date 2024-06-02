package comeayoua.growthspace.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun rememberAppUiState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
    isUserLoggedIn: Boolean
): AppUiState {
    return remember(
        windowSizeClass
    ){
        AppUiState(
            windowSizeClass,
            navController,
            isUserLoggedIn
        )
    }
}

@Stable
class AppUiState(
    private val windowSizeClass: WindowSizeClass,
    val navController: NavHostController,
    val isUserLoggedIn: Boolean
) {
    val shouldShowBottomNavigationBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavigationRail: Boolean
        get() = !shouldShowBottomNavigationBar
}