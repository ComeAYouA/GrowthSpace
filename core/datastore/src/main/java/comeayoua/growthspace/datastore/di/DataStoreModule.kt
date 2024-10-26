package comeayoua.growthspace.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import comeayoua.growthspace.datastore.UserDataStore
import comeayoua.growthspace.datastore.VersionListStore
import comeayoua.growthspace.datastore.prefernces.UserDataStoreImpl
import comeayoua.growthspace.datastore.prefernces.VersionListStoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_DATA_NAME = "user_data"

internal annotation class UserPreferencesDataStore

@InstallIn(SingletonComponent::class)
@Module
internal interface DataStoreModule {

    @Binds
    fun binds_UserDataStoreImpl_to_UserDataStore(input: UserDataStoreImpl): UserDataStore

    @Binds
    fun binds_VersionListStoreImpl_to_VersionListStore(input: VersionListStoreImpl): VersionListStore

    companion object{
        private val Context.userDataStore by preferencesDataStore(name = USER_DATA_NAME)

        @UserPreferencesDataStore
        @Singleton
        @Provides
        fun providesUserDataStore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> = context.userDataStore
    }
}