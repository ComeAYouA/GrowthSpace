package comeayoua.growthspace.projects

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import comeayoua.growthspace.core.ui.R
import comeayoua.growthspace.model.Project
import comeayoua.growthspace.model.Schedule
import comeayoua.growthspace.model.ScheduleType
import comeayoua.growthspace.model.ScheduleValue
import comeayoua.growthspace.ui.widgets.WeekRow
import comeayoua.growthspace.ui.widgets.WeekRowValue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsListScreen(
    modifier: Modifier = Modifier,
    onCreateProject: () -> Unit,
    viewModel: ProjectsListViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState { 2 }

    val topBarState = rememberTopAppBarState()

    val projectsState by viewModel.projectsState.collectAsState()

    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topBarState
    )

    val isTopBarCollapsed by remember {
        derivedStateOf {
            topBarState.collapsedFraction >= 0.9f
        }
    }

    val content:  @Composable (BoxScope.() -> Unit) = {
        Column {
            Tabs(
                pagerState = pagerState,
                divider = { isTopBarCollapsed }
            )
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
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                        projects = projectsState,
                        onScheduleChanged = {}
                    )
                    1 -> Projects(
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                        projects = listOf(),
                        onScheduleChanged = {}
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
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
                        onClick = {  }
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
                        onClick = onCreateProject
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
        Box(modifier = Modifier.padding(paddingValues), content = content)
    }
}

@Composable
fun Projects(
    modifier: Modifier = Modifier,
    projects: List<Project>,
    onScheduleChanged: (Schedule) -> Unit
){

    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp),
    ) {
        this.items(projects){ project ->
            ProjectItem(
                title = project.name,
                schedule = project.schedule,
                onScheduleChanged = onScheduleChanged,
            )
        }
    }
}

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
        HorizontalDivider(modifier = Modifier
            .graphicsLayer {
                alpha = dividerAnim.value
            }
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun ProjectItem(
    modifier: Modifier = Modifier,
    title: String,
    schedule: Schedule,
    onScheduleChanged: (Schedule) -> Unit
){
    val tabs = remember {
        mutableStateOf(
            WeekRowValue(
                monday = schedule.value?.days?.contains(1) ?: (schedule.type == ScheduleType.DAILY),
                tuesday = schedule.value?.days?.contains(2) ?: (schedule.type == ScheduleType.DAILY),
                wednsday = schedule.value?.days?.contains(3) ?: (schedule.type == ScheduleType.DAILY),
                thusday = schedule.value?.days?.contains(4) ?: (schedule.type == ScheduleType.DAILY),
                friday = schedule.value?.days?.contains(5) ?: (schedule.type == ScheduleType.DAILY),
                saturday = schedule.value?.days?.contains(6) ?: (schedule.type == ScheduleType.DAILY),
                sunday = schedule.value?.days?.contains(7) ?: (schedule.type == ScheduleType.DAILY),
            )
        )
    }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp)
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
                    .padding(horizontal = 16.dp),
            ){
                Text(
                    text = schedule.type.name.lowercase().replaceFirstChar { it.uppercaseChar() },
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal
                )

                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        WeekRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp
                ),
            currentDayIdx = 1,
            tabs = tabs,
            onTabClick = { idx, selected ->
                val newValue = when{
                    (schedule.value?.days?.contains(idx) == selected) -> schedule.value?.days
                    else -> {
                        schedule.value?.days?.toMutableList()?.apply {
                            if (selected) add(idx) else remove(idx)
                        }
                    }
                }
                onScheduleChanged.invoke(
                    schedule.copy(value = newValue?.let { ScheduleValue(it) })
                )
            }
        )
    }
}