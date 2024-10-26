package comeayoua.growthspace.sync.di

import comeayoua.growthspace.sync.SyncManager
import comeayoua.growthspace.sync.worker.project.SyncManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface SyncModule {
    @Binds
    fun bind_ProjectSyncManagerImpl_to_ProjectSyncManager(input: SyncManagerImpl): SyncManager
}