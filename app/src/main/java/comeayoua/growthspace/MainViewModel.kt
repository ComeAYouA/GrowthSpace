package comeayoua.growthspace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comeayoua.growthspace.domain.GetUserDataUseCase
import comeayoua.growthspace.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase
): ViewModel() {

    var uiState: StateFlow<MainScreenUiState> = flow{
        val state = MainScreenUiState.IsReady(getUserData())
        emit(state)
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), MainScreenUiState.Loading
    )

    private suspend fun getUserData(): UserData = getUserDataUseCase()
}