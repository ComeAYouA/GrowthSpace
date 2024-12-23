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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import comeayoua.growthspace.onboarding.ui.model.rememberOnBoardingPages

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onStartButtonClicked: () -> Unit = {},
    viewModel: OnBoardingViewModel = hiltViewModel()
){
    val pagerState = rememberPagerState { 3 }
    val onBoardingPages = rememberOnBoardingPages()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ){
            HorizontalPager(
                modifier = Modifier,
                state = pagerState
            ) { page ->
                OnBoardingPage(
                    lead = onBoardingPages.pages[page].lead,
                    description = onBoardingPages.pages[page].desc,
                    imgId = onBoardingPages.pages[page].imgId,
                )
            }

            PagerNavigation(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                pagerState = pagerState
            )

            GoForwardButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp),
                isVisible = { pagerState.currentPage == pagerState.pageCount - 1 },
                onClick = {
                    viewModel.saveOnBoardingStatus()
                    onStartButtonClicked()
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun OnBoardingScreenPreview(
    modifier: Modifier = Modifier
){
    val pagerState = rememberPagerState { 3 }
    val onBoardingPages = rememberOnBoardingPages()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ){
            HorizontalPager(
                modifier = Modifier,
                state = pagerState
            ) { page ->
                OnBoardingPage(
                    lead = onBoardingPages.pages[page].lead,
                    description = onBoardingPages.pages[page].desc,
                    imgId = onBoardingPages.pages[page].imgId,
                )
            }

            PagerNavigation(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                pagerState = pagerState
            )

            GoForwardButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp),
                isVisible = { pagerState.currentPage == pagerState.pageCount - 1 },
                onClick = {
                }
            )
        }
    }
}

@Composable
internal fun GoForwardButton(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    onClick: () -> Unit
) {
    val getStartedButtonAlpha by animateFloatAsState(
        targetValue = if (isVisible.invoke()) 1f else 0f,
        label = "getStartedButtonAlpha",
    )

    Button(
        modifier = modifier
            .graphicsLayer {
                this.translationY = 100 - 100 * getStartedButtonAlpha
                this.alpha = getStartedButtonAlpha
            }
            .padding(bottom = 10.dp),
        enabled = isVisible.invoke(),
        onClick = onClick
    ){
        Text(
            modifier = Modifier.graphicsLayer {
                this.alpha = getStartedButtonAlpha
            },
            text = "Get started"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PagerNavigation(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {
    Row(
        modifier
            .wrapContentHeight()
            .fillMaxWidth(),
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun OnBoardingPage(
    lead: String,
    description: String,
    @DrawableRes imgId: Int,
    modifier: Modifier = Modifier,
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
            modifier = modifier
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
                modifier = Modifier,
                text = lead,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                lineHeight = 32.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

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
