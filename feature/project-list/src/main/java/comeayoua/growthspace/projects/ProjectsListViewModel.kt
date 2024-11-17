package comeayoua.growthspace.projects

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comeayoua.growthspace.domain.project.AddProjectsUseCase
import comeayoua.growthspace.domain.project.GetProjectsUseCase
import comeayoua.growthspace.domain.project.UpdateProjectsUseCase
import comeayoua.growthspace.sync.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsListViewModel @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val syncManager: SyncManager
): ViewModel() {
    init {
        Log.d("myTag", "init")
        viewModelScope.launch {
            getProjectsUseCase.invoke().collect {
                Log.d("myTag", "$it")
            }
        }
    }
}