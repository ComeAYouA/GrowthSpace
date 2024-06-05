package comeayoua.growthspace.auth.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.gotrue.Auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val USER_SHARED_PREF_KEY = "USER_SHARED_PREF_KEY"
private const val TOKEN_KEY = "TOKEN_KEY"
private const val ONBOARDING_STATUS_KEY = "ONBOARDING_STATUS_KEY"


class UserDataUtil @Inject constructor(
    @ApplicationContext private val context: Context,
){
    fun getUserToken(): String? {
        val sharedPref = context.getSharedPreferences(
            USER_SHARED_PREF_KEY,
            Context.MODE_PRIVATE
        )

        return sharedPref.getString(TOKEN_KEY, null)
    }

    fun getOnBoardingStatus(): Boolean {
        val sharedPref = context.getSharedPreferences(
            USER_SHARED_PREF_KEY,
            Context.MODE_PRIVATE
        )

        return sharedPref.getBoolean(ONBOARDING_STATUS_KEY, false)
    }

    fun saveUserToken(token: String?) {
        val sharedPref = context.getSharedPreferences(
            USER_SHARED_PREF_KEY, Context.MODE_PRIVATE
        )

        sharedPref.edit().putString(TOKEN_KEY, token).apply()
    }

    fun saveOnboardingStatus(isOnboarded: Boolean){
        val sharedPref = context.getSharedPreferences(
            USER_SHARED_PREF_KEY, Context.MODE_PRIVATE
        )
        sharedPref.edit().putBoolean(ONBOARDING_STATUS_KEY, isOnboarded).apply()
    }

}