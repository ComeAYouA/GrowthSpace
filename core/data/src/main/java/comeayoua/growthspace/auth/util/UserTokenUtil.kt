package comeayoua.growthspace.auth.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val USER_SHARED_PREF_KEY = "USER_SHARED_PREF_KEY"
private const val TOKEN_KEY = "TOKEN_KEY"

class UserTokenUtil @Inject constructor(
    @ApplicationContext private val context: Context
){
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun isUserLoggedIn(): Boolean = true

    fun getLoginToken(): String? {
        val sharedPref = context.getSharedPreferences(
            USER_SHARED_PREF_KEY,
            Context.MODE_PRIVATE
        )

        return sharedPref.getString(TOKEN_KEY, null)
    }

    suspend fun saveLoginToken(token: String?) {
        coroutineScope.launch(Dispatchers.IO) {
            val sharedPref = context.getSharedPreferences(
                USER_SHARED_PREF_KEY, Context.MODE_PRIVATE
            )
            sharedPref.edit().putString(TOKEN_KEY, token).apply()
        }
    }

}