package comeayoua.growthspace.login

sealed interface LoginScreenState{
    object Enabled: LoginScreenState
    object SyncingWithGoogle: LoginScreenState
    object LoginningUp: LoginScreenState

    object SigningUp: LoginScreenState
}