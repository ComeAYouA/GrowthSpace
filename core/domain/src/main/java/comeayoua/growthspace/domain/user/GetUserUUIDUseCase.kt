package comeayoua.growthspace.domain.user

import io.github.jan.supabase.auth.Auth
import javax.inject.Inject

class GetUserUUIDUseCase @Inject constructor(
    private val auth: Auth
){
    operator fun invoke() = auth.currentUserOrNull()?.id
}