package mas.ca.humanprofiler.data.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mas.ca.humanprofiler.data.datasources.local.entities.LocalProfile

@Database(entities = [LocalProfile::class], version = 1)
abstract class LocalStorageDatabase : RoomDatabase() {
    abstract val profileDao: ProfileDao

    companion object {
        private const val DATABASE_NAME = "hp_local"

        var INSTANCE: LocalStorageDatabase? = null

        @Synchronized
        fun create(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, LocalStorageDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}