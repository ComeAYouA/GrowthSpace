package comeayoua.growthspace.onboarding.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable

@Stable
data class OnBoardingPageResource(
    val lead: String,
    val desc: String,
    @DrawableRes val imgId: Int
)