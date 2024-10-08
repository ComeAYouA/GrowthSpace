package comeayoua.growthspace.login.utils

import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import comeayoua.growthspace.feature.login.BuildConfig
import java.security.MessageDigest

fun rawNonceToGoogleOptions(rawNonce: String): GetGoogleIdOption{
    val bytes = rawNonce.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it)}

    val googleOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.clientId)
        .setNonce(hashedNonce)
        .build()

    return googleOption
}