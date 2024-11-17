package comeayoua.growthspace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comeayoua.growthspace.domain.user.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase
): ViewModel() {

    var uiState: StateFlow<MainScreenUiState> =
        getUserDataUseCase().map { userData ->
            val state = MainScreenUiState.IsReady(userData)
            state
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), MainScreenUiState.Loading
    )
}