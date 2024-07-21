package comeayoua.growthspace.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import comeayoua.growthspace.datastore.UserDataStore
import comeayoua.growthspace.datastore.prefernces.SessionManagerImpl
import comeayoua.growthspace.datastore.prefernces.UserDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.gotrue.SessionManager

private const val USER_DATA_NAME = "user_data"

internal annotation class UserPreferencesDataStore

@InstallIn(SingletonComponent::class)
@Module
internal interface DataStoreModule {

    @Binds
    fun binds_UserDataStoreImpl_to_UserDataStore(input: UserDataStoreImpl): UserDataStore
    @Binds
    fun binds_SessionManagerImpl_to_SessionManager(input: SessionManagerImpl): SessionManager

    companion object{
        private val Context.dataStore by preferencesDataStore(name = USER_DATA_NAME)

        @UserPreferencesDataStore
        @Provides
        fun providesDataStore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> = context.dataStore
    }
}