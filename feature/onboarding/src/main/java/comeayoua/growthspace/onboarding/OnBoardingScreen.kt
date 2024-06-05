package comeayoua.growthspace.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import comeayoua.growthspace.core.ui.R


@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onStartButtonClicked: () -> Unit = {}
){
    val pagerState = rememberPagerState { 3 }
    val pages =
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

    Box(
        modifier = modifier
    ){
        HorizontalPager(
            modifier = modifier,
            state = pagerState
        ) { page ->
            OnBoardingHeader(
                lead = pages[page].lead,
                description = pages[page].desc,
                imgId = pages[page].imgId
            )
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val getStartedButtonAlpha by animateFloatAsState(
                targetValue = if (pagerState.currentPage == pagerState.pageCount - 1) 1f else 0f,
                label = "getStartedButtonAlpha",
            )

            Button(
                modifier = Modifier.graphicsLayer {
                    this.translationY = 100 - 100 * getStartedButtonAlpha
                    this.alpha = getStartedButtonAlpha
                }
                    .padding(bottom = 10.dp),
                enabled = pagerState.currentPage == pagerState.pageCount - 1,
                onClick = onStartButtonClicked
            ){
                Text(
                    modifier = Modifier.graphicsLayer {
                        this.alpha = getStartedButtonAlpha
                    },
                    text = "Get started"
                )
            }

            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color by animateColorAsState(
                        targetValue = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray,
                        label = ""
                    )

                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun OnBoardingHeader(
    lead: String,
    description: String,
    @DrawableRes imgId: Int,
    modifier: Modifier = Modifier
){
    val density = LocalDensity.current

    val headerBottom = remember{
        mutableStateOf(0.dp)
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
    ){
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))

        Column(
            modifier = Modifier
                .onGloballyPositioned {
                    headerBottom.value = with(density) {
                        (it.positionInWindow().y + it.size.height).toDp()
                    }
                }
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                ),
        ) {
            Text(
                text = lead,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = description,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.2f))

        Image(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            painter = painterResource(id = imgId),
            contentDescription = description,
            contentScale = ContentScale.Fit
        )

    }
}

@Stable
data class OnBoardingPageResource(
    val lead: String,
    val desc: String,
    @DrawableRes val imgId: Int
)
