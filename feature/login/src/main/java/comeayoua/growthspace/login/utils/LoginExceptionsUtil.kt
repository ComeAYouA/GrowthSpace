package comeayoua.growthspace.login.utils

import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException

fun handleLoginException(e: Exception): Result<Boolean>{
    return when(e){
        is NoCredentialException -> Result.failure(RuntimeException("Google credentials are missing"))
        is GetCredentialCancellationException -> Result.success(false)
        is CreateCredentialCancellationException -> Result.success(false)
        else -> { Result.failure(e) }
    }
}