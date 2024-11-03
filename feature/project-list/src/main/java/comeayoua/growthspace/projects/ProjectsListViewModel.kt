package comeayoua.growthspace.projects

import androidx.lifecycle.ViewModel
import comeayoua.growthspace.domain.project.AddProjectUseCase
import comeayoua.growthspace.domain.project.GetProjectsUseCase
import comeayoua.growthspace.domain.project.UpdateProjectsUseCase
import comeayoua.growthspace.sync.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectsListViewModel @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val addProjectUseCase: AddProjectUseCase,
    private val updateProjectsUseCase: UpdateProjectsUseCase,
    private val syncManager: SyncManager
): ViewModel() {

    fun createProject(){

    }
}