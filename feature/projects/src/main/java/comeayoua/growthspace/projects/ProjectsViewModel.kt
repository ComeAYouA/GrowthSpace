package comeayoua.growthspace.projects

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comeayoua.growthspace.domain.project.AddProjectsUseCase
import comeayoua.growthspace.domain.project.GetProjectsUseCase
import comeayoua.growthspace.model.Project
import comeayoua.growthspace.model.ProjectType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val addProjectUseCase: AddProjectsUseCase

): ViewModel() {
    init {
        viewModelScope.launch {
            getProjectsUseCase().collect{
                Log.d("myTag", "project collected: $it")
            }
        }
    }

    fun addProject(){
        viewModelScope.launch {
            Log.d("myTag", "adding project...")
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
                    listOf()
                )
            )
        }
    }
}