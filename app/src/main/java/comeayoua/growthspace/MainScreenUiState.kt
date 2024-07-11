package comeayoua.growthspace

import comeayoua.growthspace.model.UserData

sealed interface MainScreenUiState{
    object Loading: MainScreenUiState
    data class IsReady(val userData: UserData): MainScreenUiState
}