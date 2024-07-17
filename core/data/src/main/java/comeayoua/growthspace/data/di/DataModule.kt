package comeayoua.growthspace.data.di

import comeayoua.growthspace.data.UserDataRepository
import comeayoua.growthspace.data.repository.UserDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface DataModule {
    @Binds
    fun binds_UserDataRepositoryImpl_to_UserDataRepository(input: UserDataRepositoryImpl): UserDataRepository
}