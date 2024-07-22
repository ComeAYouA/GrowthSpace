package comeayoua.growthspace.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comeayoua.growthspace.domain.project.AddProjectUseCase
import comeayoua.growthspace.domain.project.GetProjectsUseCase
import comeayoua.growthspace.model.Project
import comeayoua.growthspace.model.ProjectSchedule
import comeayoua.growthspace.model.ProjectType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val addProjectUseCase: AddProjectUseCase

): ViewModel() {
    init {
        viewModelScope.launch {
            getProjectsUseCase().collect{}
        }
    }

    fun addProject(){
        viewModelScope.launch {
            addProjectUseCase(
                Project(
                    0,
                    "First",
                    "Empty",
                    false,
                    ProjectType.Habit,
                    LocalDateTime.parse("2024-07-20T16:13:18"),
                    0,
                    UUID.randomUUID(),
                    0,
                    ProjectSchedule(thursday = true)
                )
            )
        }
    }
}