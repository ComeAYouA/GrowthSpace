package comeayoua.growthspace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comeayoua.growthspace.domain.GetUserDataUseCase
import comeayoua.growthspace.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase
): ViewModel() {

    var isReady: StateFlow<Boolean> = flow{
        getUserData()
        emit(true)
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), false
    )

    private val _userData = MutableStateFlow(
        UserData(
            isUserLoggedIn = false,
            isOnboarded = false)
    )
    val userData = _userData.asStateFlow()

    private fun getUserData(){
        viewModelScope.launch {
            _userData.value = getUserDataUseCase()
        }
    }
}