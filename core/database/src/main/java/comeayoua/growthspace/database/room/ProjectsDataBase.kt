package comeayoua.growthspace.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import comeayoua.growthspace.database.ProjectsDao
import comeayoua.growthspace.database.ProjectsTypeConverter
import comeayoua.growthspace.database.model.ProjectEntity

@Database(entities = [ ProjectEntity::class], version = 1)
@TypeConverters(ProjectsTypeConverter::class)
abstract class ProjectsDataBase: RoomDatabase() {
    abstract fun projectsDao(): ProjectsDao
}