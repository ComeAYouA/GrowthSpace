package comeayoua.growthspace.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import comeayoua.growthspace.datastore.UserDataStore
import comeayoua.growthspace.datastore.UserDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private const val USER_PREFERENCES_NAME = "user_preferences"

internal annotation class UserPreferencesDataStore

@InstallIn(SingletonComponent::class)
@Module
internal interface DataStoreModule {

    @Binds
    fun binds_UserDataStoreImpl_to_UserDataStore(input: UserDataStoreImpl): UserDataStore

    companion object{
        private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

        @UserPreferencesDataStore
        @Provides
        fun providesDataStore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> = context.dataStore
    }
}