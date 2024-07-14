package comeayoua.growthspace.login.ui.stateholders
sealed interface FormState{
    object Valid: FormState
    data class Error(val message: String): FormState
}