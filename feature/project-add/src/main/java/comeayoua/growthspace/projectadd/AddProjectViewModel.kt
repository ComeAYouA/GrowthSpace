package comeayoua.growthspace.projectadd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comeayoua.growthspace.domain.project.AddProjectsUseCase
import comeayoua.growthspace.domain.user.GetUserUUIDUseCase
import comeayoua.growthspace.model.HabitSchedule
import comeayoua.growthspace.model.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
    private val addProjectsUseCase: AddProjectsUseCase,
    private val getUserUUIDUseCase: GetUserUUIDUseCase
) : ViewModel() {
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()
    fun setProjectName(name: String) = _name.update { name }

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()
    fun setProjectDescription(description: String) = _description.update { description }

    private val _repeat = MutableStateFlow(true)
    val repeat = _repeat.asStateFlow()
    fun setRepeat(repeat: Boolean) = _repeat.update { repeat }

    private val _remind = MutableStateFlow(true)
    val remind = _remind.asStateFlow()
    fun setRemind(remind: Boolean) = _remind.update { remind }

    private val _schedule = MutableStateFlow(HabitSchedule())
    val schedule = _schedule.asStateFlow()
    fun setSchedule(schedule: HabitSchedule) = _schedule.update { schedule }

    private val _isPublic = MutableStateFlow(true)
    val isPublic = _isPublic.asStateFlow()
    fun setPublicStatus(isPublic: Boolean) = _isPublic.update { isPublic }

    fun addHabit(){
        val userUUID = getUserUUIDUseCase.invoke() ?: return

        viewModelScope.launch {
            addProjectsUseCase.invoke(
                listOf(
                    Project(
                        id = UUID.randomUUID(),
                        name = name.value,
                        description = description.value,
                        repeat = repeat.value,
                        remind = remind.value,
                        schedule = schedule.value,
                        ownerId = UUID.fromString(userUUID),
                        progress = 0,
                        streak = 0,
                        createdAt = LocalDateTime.now().toKotlinLocalDateTime(),
                        isPublic = isPublic.value,
                    )
                )
            )
        }
    }
}