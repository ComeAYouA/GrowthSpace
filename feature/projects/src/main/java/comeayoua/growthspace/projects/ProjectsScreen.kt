package comeayoua.growthspace.projects

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun ProjectsScreen(
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val projectsListState = rememberLazyListState()
    val barsHeight = remember {
        mutableStateOf(164.dp)
    }
    val contentPadding by remember {
        derivedStateOf {
            barsHeight.value
        }
    }
    val offsetEnd = with(density) { 50.dp.toPx() }

    val interactionBarOffset = remember {
        derivedStateOf {
            if (
                projectsListState.firstVisibleItemScrollOffset <= offsetEnd &&
                projectsListState.firstVisibleItemIndex == 0
                ) {
                projectsListState.firstVisibleItemScrollOffset.toFloat()
            } else {
                offsetEnd
            }
        }
    }
    val titleVisible by remember {
        derivedStateOf { interactionBarOffset.value > with(density) { 32.dp.toPx() } }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        Projects(
            state = projectsListState,
            contentPadding = PaddingValues(top = contentPadding)
        )
        Column(
            modifier = Modifier
                .onGloballyPositioned {
                    barsHeight.value = with(density) { it.size.height.toDp() + 8.dp }
                }
        ) {
            TopBar(
                titleVisible = titleVisible
            )
            InteractionBar(
                titleVisible = titleVisible,
                offset = interactionBarOffset
            )
        }
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    titleVisible: Boolean
){

    val configuration = LocalConfiguration.current
    val windowWidth = configuration.screenWidthDp.dp
    val horizontalMargin = remember { 16.dp }
    val iconsSize = remember { 32.dp }
    val titleAnimation = animateFloatAsState(
        targetValue = if (titleVisible){
            1f
        } else {
            0f
        },
        label = "top bar title alpha animation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 16.dp, horizontal = horizontalMargin)
    ){
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(iconsSize)
                .clickable { Log.d("myTag", "menu") },
            imageVector = Icons.Default.Menu,
            contentDescription = "menu"
        )

        Text(
            modifier = Modifier
                .graphicsLayer {
                    alpha = titleAnimation.value
                }
                .align(Alignment.Center)
                .width(windowWidth - horizontalMargin * 4 - iconsSize * 2),
            text = "Projects",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(iconsSize),
            imageVector = Icons.Default.Add,
            contentDescription = "menu"
        )
    }
}

@Composable
fun InteractionBar(
    modifier: Modifier = Modifier,
    titleVisible: Boolean = true,
    offset: State<Float> = remember { derivedStateOf { 0f } }
){

    val titleAnimation = animateFloatAsState(
        targetValue = if (!titleVisible){
            1f
        } else {
            0f
        },
        label = "top bar title alpha animation"
    )

    Column (
        modifier = modifier
            .graphicsLayer {
                this.translationY = -offset.value
            }
            .fillMaxWidth()
    ){
        Text(
            modifier = Modifier
                .graphicsLayer {
                    alpha = 1 - offset.value / 32.dp.toPx()
                }
                .padding(horizontal = 16.dp),
            text = "Projects",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp
        )

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 16.dp, end = 16.dp, start = 16.dp, bottom = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Yours",
                        fontSize = 16.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Followed",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .graphicsLayer { 
                        alpha = 1f - titleAnimation.value
                    }
            )
        }
    }
}

@Composable
fun Projects(
    modifier: Modifier = Modifier,
    state: LazyListState,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding,
        state = state
    ) {
        (0..10).forEach {
            this.item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(80.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}