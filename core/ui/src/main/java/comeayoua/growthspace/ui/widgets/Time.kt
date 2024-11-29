package comeayoua.growthspace.ui.widgets

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal val dayOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sut", "Sun")

data class WeekRowValue(
    var monday: Boolean,
    var tuesday: Boolean,
    var wednsday: Boolean,
    var thusday: Boolean,
    var friday: Boolean,
    var saturday: Boolean,
    var sunday: Boolean,
)

@Composable
fun WeekRow(
    modifier: Modifier = Modifier,
    currentDayIdx: Int,
    tabs: State<WeekRowValue>,
    showToday: Boolean = true,
    onTabClick: (idx: Int, isSelected: Boolean) -> Unit = {_, _ -> },
){
    val tabsState = remember { tabs.value }
    val selectedTabs = remember {
        listOf(
            tabsState.monday,
            tabsState.tuesday,
            tabsState.wednsday,
            tabsState.thusday,
            tabsState.friday,
            tabsState.saturday,
            tabsState.sunday
        )
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        selectedTabs.forEachIndexed { idx, selected ->
            WeekRowTab(
                dayOfWeek[idx],
                isSelected = selected,
                isToday = idx == currentDayIdx,
                showToday
            ){ isSelected -> onTabClick(idx + 1, isSelected) }
        }
    }
}

// TODO: showToday Можно переделать в context
@Composable
internal fun WeekRowTab(
    name: String = "Mon",
    isSelected: Boolean = false,
    isToday: Boolean = false,
    showToday: Boolean = true,
    onTabClick: (isSelected: Boolean) -> Unit = {}
) {
    val density = LocalDensity.current

    val interactionSource = remember { MutableInteractionSource() }

    val selected = remember { mutableStateOf(isSelected) }

    val background by animateColorAsState(
        targetValue = if (isToday && showToday) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            Color.Transparent
        },
        label = "weekRow tab background animation"
    )

    val primaryColor by animateColorAsState(
        targetValue = if (selected.value) {
            MaterialTheme.colorScheme.primary
        } else {
            Color.Transparent
        },
        label = "weekRow tab background animation"
    )

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                selected.value = !selected.value
                onTabClick(selected.value)
            }
            .drawWithContent {
                drawRoundRect(
                    background,
                    cornerRadius = with(density) {
                        CornerRadius(16.dp.toPx(), 16.dp.toPx())
                    }
                )
                drawContent()
            }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = name,
            fontSize = 10.sp,
            fontWeight = FontWeight.Light
        )
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .size(24.dp)
                .drawWithContent {
                    drawCircle(primaryColor)
                    drawContent()
                }
                .border(
                    2.dp,
                    if (!selected.value)
                        MaterialTheme.colorScheme.inversePrimary
                    else
                        Color.Transparent,
                    CircleShape
                )
        ) {
            if (selected.value) {
                Icon(
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.Center),
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
fun WeekRowPreview(){
    val tabs = remember {
        mutableStateOf(
            WeekRowValue(
                monday = true,
                tuesday = true,
                wednsday = false,
                thusday = false,
                friday = true,
                saturday = false,
                sunday = true
            )
        )
    }
    WeekRow(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(8.dp),
        currentDayIdx = 1,
        tabs = tabs,
        onTabClick = { idx, selected ->
            when(idx) {
                1 -> tabs.value = tabs.value.copy(monday = selected)
                2 -> tabs.value = tabs.value.copy(tuesday = selected)
                3 -> tabs.value = tabs.value.copy(wednsday = selected)
                4 -> tabs.value = tabs.value.copy(thusday = selected)
                5 -> tabs.value = tabs.value.copy(friday = selected)
                6 -> tabs.value = tabs.value.copy(saturday = selected)
                7 -> tabs.value = tabs.value.copy(sunday = selected)
            }
        }
    )
}