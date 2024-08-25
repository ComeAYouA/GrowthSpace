package comeayoua.growthspace.login

sealed interface LoginScreenState{
    object Enabled: LoginScreenState
    object SyncingWithGoogle: LoginScreenState
    object Loading: LoginScreenState
    object Success: LoginScreenState
    data class Error(val message: String?): LoginScreenState
}