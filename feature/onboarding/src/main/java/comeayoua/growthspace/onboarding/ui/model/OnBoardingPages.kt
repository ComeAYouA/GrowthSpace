package comeayoua.growthspace.onboarding.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import comeayoua.growthspace.core.ui.R

@Immutable
internal data class OnBoardingPages(
    val pages: List<OnBoardingPageResource>
)

@Composable
internal fun rememberOnBoardingPages(): OnBoardingPages = OnBoardingPages(
    listOf(
        OnBoardingPageResource(
            lead = stringResource(id = R.string.Onboarding_lead_1),
            desc = stringResource(id = R.string.Onboarding_desc_1),
            imgId = R.mipmap.img_onboarding_1
        ),
        OnBoardingPageResource(
            lead = stringResource(id = R.string.Onboarding_lead_2),
            desc = stringResource(id = R.string.Onboarding_desc_2),
            imgId = R.mipmap.img_onboarding_2
        ),
        OnBoardingPageResource(
            lead = stringResource(id = R.string.Onboarding_lead_3),
            desc = stringResource(id = R.string.Onboarding_desc_3),
            imgId = R.mipmap.img_onboarding_3
        ),
    )
)