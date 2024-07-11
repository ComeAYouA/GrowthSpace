package comeayoua.growthspace.login.utils

import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import comeayoua.growthspace.model.Result
import io.github.jan.supabase.exceptions.RestException

fun handleLoginException(e: Exception): Result.Error<String>{
    return when(e){
        is NoCredentialException -> Result.Error(message = "Google credentials are missing")
        is GetCredentialCancellationException -> Result.Error(message = null)
        is CreateCredentialCancellationException -> Result.Error(message = null)
        is RestException -> { Result.Error(message = e.description?:e.message?.lines()?.first()) }
        else -> { Result.Error(message = "Unknown error. Please try later") }
    }
}