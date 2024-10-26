package comeayoua.growthspace.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comeayoua.growthspace.domain.project.AddProjectUseCase
import comeayoua.growthspace.domain.project.GetProjectsUseCase
import comeayoua.growthspace.domain.project.UpdateProjectsUseCase
import comeayoua.growthspace.model.Project
import comeayoua.growthspace.model.ProjectSchedule
import comeayoua.growthspace.model.ProjectType
import comeayoua.growthspace.sync.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val addProjectUseCase: AddProjectUseCase,
    private val updateProjectsUseCase: UpdateProjectsUseCase,
    private val syncManager: SyncManager
): ViewModel() {

    fun addProject(){
        viewModelScope.launch {
            addProjectUseCase(
                listOf(
                Project(
                    UUID.randomUUID(),
                    "First UPDATED SECOND",
                    "New description updated tegtdvguv",
                    false,
                    ProjectType.Habit,
                    LocalDateTime.parse("2024-07-20T16:13:18"),
                    0,
                    UUID.fromString("fcf70b36-d77a-4a9b-a358-7044b4c7e9a6"),
                    23,
                    ProjectSchedule(thursday = true),
                    false
                ),
                    Project(
                        UUID.randomUUID(),
                        "Second UPDATED SECOND",
                        "New description updated tegtdvguv",
                        false,
                        ProjectType.Habit,
                        LocalDateTime.parse("2024-07-20T16:13:18"),
                        0,
                        UUID.fromString("fcf70b36-d77a-4a9b-a358-7044b4c7e9a6"),
                        23,
                        ProjectSchedule(thursday = true),
                        false
                    )
            )
            )
        }
    }
}