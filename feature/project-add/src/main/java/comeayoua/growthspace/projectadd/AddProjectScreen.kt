package comeayoua.growthspace.projectadd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import comeayoua.growthspace.model.Schedule
import comeayoua.growthspace.model.ScheduleType
import comeayoua.growthspace.model.ScheduleValue
import comeayoua.growthspace.ui.widgets.DefaultTextField
import comeayoua.growthspace.ui.widgets.WeekRow
import comeayoua.growthspace.ui.widgets.WeekRowValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectScreen(
    onAddProject: () -> Unit,
    viewModel: AddProjectViewModel = hiltViewModel()
){
    val topBarState = rememberTopAppBarState()

    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topBarState
    )

    val layoutDirection = LocalLayoutDirection.current

    val nameState = viewModel.name.collectAsState()
    val descriptionState = viewModel.description.collectAsState()
    val scheduleState by viewModel.schedule.collectAsState()
    val repeatableChecked by viewModel.repeat.collectAsState()
    val reminderChecked by viewModel.remind.collectAsState()
    val publicChecked by viewModel.isPublic.collectAsState()

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                onClick = {
                    viewModel.addHabit()
                    onAddProject.invoke()
                }
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add habit"
                )
            }
        },
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = "Create Habit",
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier,
                        onClick = onAddProject
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "menu"
                        )
                    }
                },
                scrollBehavior = topAppBarScrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn (
           modifier = Modifier
               .padding(
                   top = paddingValues.calculateTopPadding(),
                   bottom = paddingValues.calculateBottomPadding(),
                   start = paddingValues.calculateStartPadding(layoutDirection) + 16.dp,
                   end = paddingValues.calculateEndPadding(layoutDirection) + 16.dp
               )
               .fillMaxSize()
               .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
        ) {
            item {
                ChevronSection(
                    text = "Choose tree skin"
                )
            }
            item {
                DefaultTextField(
                    modifier = Modifier
                        .padding(top = 16.dp),
                    hint = "Title",
                    query = nameState,
                    onValueChanged = viewModel::setProjectName
                )
            }
            item {
                DefaultTextField(
                    modifier = Modifier
                        .padding(top = 16.dp),
                    hint = "Description",
                    query = descriptionState,
                    onValueChanged = viewModel::setProjectDescription,
                    singleLine = false
                )
            }
            item {
                SelectableSection(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Repeat",
                    checked = repeatableChecked,
                    onCheckedChanged = viewModel::setRepeat
                )
            }
            item {
                if (repeatableChecked){
                    RepeatSettingsSection(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        schedule = scheduleState,
                        onScheduleChanged = viewModel::setSchedule
                    )
                }
            }
            item {
                SelectableSection(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Reminder",
                    checked = reminderChecked,
                    onCheckedChanged = viewModel::setRemind
                )
            }
            item {
                SelectableSection(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Public",
                    checked = publicChecked,
                    onCheckedChanged = viewModel::setPublicStatus
                )
            }
        }
    }
}

@Composable
fun ChevronSection(
    modifier: Modifier = Modifier,
    text: String
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = text,
        )
        Icon(
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
            contentDescription = null
        )
    }
}

@Composable
fun SelectableSection(
    modifier: Modifier = Modifier,
    checked: Boolean,
    text: String,
    onCheckedChanged: (Boolean) -> Unit
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = text,
        )

        Switch(
            modifier = Modifier
                .height(20.dp)
                .align(Alignment.CenterEnd),
            checked = checked,
            onCheckedChange = onCheckedChanged,
            colors = SwitchDefaults.colors(
                uncheckedBorderColor = Color.Transparent,
            ),
        )
    }
}

// TODO: Set current day
@Composable
fun RepeatSettingsSection(
    modifier: Modifier = Modifier,
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
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(all = 8.dp)
    ){
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Weekly",
        )

        WeekRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp
                ),
            currentDayIdx = 1,
            showToday = false,
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
