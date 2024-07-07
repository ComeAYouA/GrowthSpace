package comeayoua.growthspace.login

sealed interface LoginScreenState{
    object Disabled: LoginScreenState
    object Enabled: LoginScreenState
}