package comeayoua.growthspace

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comeayoua.growthspace.auth.util.UserTokenUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenUtil: UserTokenUtil
): ViewModel() {

    var isReady: StateFlow<Boolean> = flow{
        checkToken()
        emit(true)
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), false
    )

    var isUserLoggedIn = false

    private suspend fun checkToken(){
        isUserLoggedIn = true
    }
}