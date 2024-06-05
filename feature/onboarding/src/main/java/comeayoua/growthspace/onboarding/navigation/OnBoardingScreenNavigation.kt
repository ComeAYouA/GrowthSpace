package comeayoua.growthspace.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import comeayoua.growthspace.onboarding.OnBoardingScreen

const val ONBOARDING_ROUTE = "onboarding_route"
private const val URI_PATTERN_LINK = "https://www.comayoua.growthspace/onboarding"

fun NavController.navigateToOnboardingScreen(navOptions: NavOptions)
        = this.navigate(ONBOARDING_ROUTE, navOptions)

fun NavGraphBuilder.onBoardingScreen(
    onStartButtonClicked: () -> Unit
) {
    composable(
        route = ONBOARDING_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern = URI_PATTERN_LINK },
        ),
    ) {
        OnBoardingScreen(
            onStartButtonClicked = onStartButtonClicked
        )
    }
}
