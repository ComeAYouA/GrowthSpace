package comeayoua.growthspace.login.navigation

import comeayoua.growthspace.login.ui.LoginScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink

const val LOGIN_ROUTE = "login_route"
private const val URI_PATTERN_LINK = "https://www.comayoua.growthspace/login"

fun NavController.navigateToLoginScreen(){
    this.navigate(LOGIN_ROUTE)
}
fun NavGraphBuilder.loginScreen(
    onLogin: () -> Unit = {}
) {
    composable(
        route = LOGIN_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern = URI_PATTERN_LINK },
        ),
    ) {
        LoginScreen(
            onLogin = onLogin
        )
    }
}
