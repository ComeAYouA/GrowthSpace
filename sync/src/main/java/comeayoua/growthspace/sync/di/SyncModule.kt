package comeayoua.growthspace.sync.di

import comeayoua.growthspace.sync.ProjectSyncManager
import comeayoua.growthspace.sync.source.ProjectSyncManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface SyncModule {
    @Binds
    fun bind_ProjectSyncManagerImpl_to_ProjectSyncManager(input: ProjectSyncManagerImpl): ProjectSyncManager
}