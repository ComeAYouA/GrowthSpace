package comeayoua.growthspace.projects

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import comeayoua.growthspace.core.ui.R
import comeayoua.growthspace.model.ProjectSchedule
import kotlinx.coroutines.launch

@Preview
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProjectsViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState { 2 }

    val topBarState = rememberTopAppBarState()

    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topBarState
    )

    val isTopBarCollapsed = remember {
        derivedStateOf {
            topBarState.collapsedFraction >= 0.9f
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceBright,
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = "Projects",
                        fontWeight = FontWeight.ExtraBold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier,
                        onClick = { Log.d("myTag", "menu") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "menu"
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier,
                        onClick = { viewModel.addProject() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "new project"
                        )
                    }
                },
                scrollBehavior = topAppBarScrollBehavior
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Tabs(
                pagerState = pagerState,
                divider = { isTopBarCollapsed.value }
            )
            Box(
                modifier = modifier
                    .fillMaxSize()
            ){
                HorizontalPager(
                    state = pagerState,
                    flingBehavior = PagerDefaults.flingBehavior(
                        state = pagerState,
                        snapAnimationSpec = spring(
                            stiffness = Spring.StiffnessMedium
                        ),
                        snapPositionalThreshold = 0.3f
                    )
                ) { page ->
                    when(page){
                        0 -> Projects(
                            modifier = Modifier.fillMaxSize(),
                            items = 10,
                            nestedScrollConnection = topAppBarScrollBehavior.nestedScrollConnection
                        )
                        1 -> Projects(
                            modifier = Modifier.fillMaxSize(),
                            items = 11,
                            nestedScrollConnection = topAppBarScrollBehavior.nestedScrollConnection
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun Projects(
    modifier: Modifier = Modifier,
    nestedScrollConnection: NestedScrollConnection,
    items: Int = 0
){

    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .nestedScroll(nestedScrollConnection),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp),
    ) {
        this.items(items){
            ProjectItem(
                title = "Tree #$it"
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    divider: () -> Boolean = { false }
){
    val density = LocalDensity.current

    val dividerAnim = animateFloatAsState(
        targetValue = if (divider()) 1f else 0f,
        label = "divider anim"
    )
    val backgroundColor by animateColorAsState(
        targetValue = if (divider())
            MaterialTheme.colorScheme.surfaceBright
        else
            MaterialTheme.colorScheme.background,
        label = "divider anim"
    )

    val currentTab = remember {
        derivedStateOf {
            pagerState.currentPage + 1
        }
    }

    val firstTabWidth = remember { mutableStateOf( with(density) { 40.sp.toDp() } ) }

    val secondTabWidth = remember { mutableStateOf( with(density) { 90.sp.toDp() } ) }

    val textHeight = remember { mutableStateOf( with(density) { 18.sp.toDp() } ) }

    val tabOffset by animateDpAsState(
        targetValue = if (currentTab.value == 1) 0.dp else firstTabWidth.value,
        label = "tab animation"
    )

    val interactionSource = remember { MutableInteractionSource() }

    val coroutineScope = rememberCoroutineScope()


    Box(
        modifier = modifier
            .drawWithContent {
                this.drawRect(backgroundColor)
                this.drawContent()
            }
    ) {
        Spacer(
            modifier = Modifier
                .offset(x = tabOffset)
                .padding(start = 16.dp)
                .height(textHeight.value)
                .width(
                    if (currentTab.value == 1) firstTabWidth.value else secondTabWidth.value
                )
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, start = 16.dp, bottom = 8.dp)
        ) {
            Text(
                modifier = Modifier
                    .onGloballyPositioned {
                        firstTabWidth.value = with(density) { it.size.width.toDp() }
                        textHeight.value = with(density) { it.size.height.toDp() }
                    }
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { coroutineScope.launch { pagerState.animateScrollToPage(0) } }
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                text = "Your",
                fontSize = 16.sp,
                color = if (currentTab.value == 1) MaterialTheme.colorScheme.onBackground else Color.Gray
            )

            Text(
                modifier = Modifier
                    .onGloballyPositioned {
                        secondTabWidth.value = with(density) { it.size.width.toDp() }
                    }
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { coroutineScope.launch { pagerState.animateScrollToPage(1) } }
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                text = "Followed",
                fontSize = 16.sp,
                color = if (currentTab.value == 2) MaterialTheme.colorScheme.onBackground else Color.Gray
            )
        }
//        HorizontalDivider(modifier = Modifier
//            .graphicsLayer {
//                alpha = dividerAnim.value
//            }
//            .fillMaxWidth()
//            .align(Alignment.BottomCenter)
//        )
    }
}

@Preview
@Composable
fun ProjectItem(
    modifier: Modifier = Modifier,
    title: String = "Project",
    streak: Int = 3,
    schedule: ProjectSchedule = ProjectSchedule(
        thursday = true, sunday = true
    )
){
    val daysOfWeek = remember {
        listOf(
            schedule.monday,
            schedule.tuesday,
            schedule.wednesday,
            schedule.thursday,
            schedule.friday,
            schedule.saturday,
            schedule.sunday,
        )
    }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(8.dp)
    ) {
        Row{
            Image(
                painter = painterResource(id = R.mipmap.img_tree_test),
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(18.dp)),
                contentDescription = "Image of a tree"
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ){
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    text = "$streak days streak",
                    color = Color.Gray
                )

                Row(
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    daysOfWeek.forEach{
                        Spacer(
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(15.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (it)
                                        MaterialTheme.colorScheme.inversePrimary
                                    else
                                        MaterialTheme.colorScheme.surfaceDim
                                )

                        )
                    }
                }
            }
        }
    }
}