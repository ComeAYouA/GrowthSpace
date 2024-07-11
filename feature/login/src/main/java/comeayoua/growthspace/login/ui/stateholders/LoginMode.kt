package comeayoua.growthspace.login.ui.stateholders

sealed interface LoginMode{
    object SignIn: LoginMode
    object SignUp: LoginMode
}