package comeayoua.growthspace.login

sealed interface LoginScreenState{
    object Loading: LoginScreenState
    object Success: LoginScreenState
}