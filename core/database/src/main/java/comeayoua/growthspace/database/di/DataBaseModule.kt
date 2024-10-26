package comeayoua.growthspace.database.di

import android.content.Context
import androidx.room.Room
import comeayoua.growthspace.database.ProjectsDao
import comeayoua.growthspace.database.room.ProjectsDataBase
import comeayoua.growthspace.database.room.ProjectsDataBaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private const val DATABASE_NAME = "projects-database"
@InstallIn(SingletonComponent::class)
@Module
internal interface DataBaseModule {

    @Binds
    fun bindsProjectsDataBaseImpl_to_ProjectsDao(impl: ProjectsDataBaseImpl): ProjectsDao

    companion object {
        @Provides
        fun provideWordsDataBase(@ApplicationContext context: Context): ProjectsDataBase{
            return Room.databaseBuilder(
                context,
                ProjectsDataBase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}