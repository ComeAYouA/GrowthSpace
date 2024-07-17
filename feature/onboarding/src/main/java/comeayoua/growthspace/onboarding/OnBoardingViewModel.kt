package comeayoua.growthspace.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comeayoua.growthspace.domain.SetOnBoardingStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val setOnBoardingStatusUseCase: SetOnBoardingStatusUseCase
): ViewModel() {
    fun saveOnBoardingStatus(){
        viewModelScope.launch {
            setOnBoardingStatusUseCase.invoke(true)
        }
    }
}