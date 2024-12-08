package comeayoua.growthspace.projects

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comeayoua.growthspace.domain.project.AddProjectsUseCase
import comeayoua.growthspace.domain.project.GetProjectsUseCase
import comeayoua.growthspace.domain.project.UpdateProjectsUseCase
import comeayoua.growthspace.sync.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsListViewModel @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val syncManager: SyncManager
): ViewModel() {
    init {
        syncManager.enqueueSync()
    }

    val projectsState = getProjectsUseCase.invoke()
        .onEach {
            Log.d("myTag", it.toString())
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            listOf()
        )
}