package mas.ca.humanprofiler.data.datasources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = LocalProfile.TABLE_NAME,
    primaryKeys = [LocalProfile.COLUMN_NAME]
)
data class LocalProfile(
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_AGE) val age: Int,
    @ColumnInfo(name = COLUMN_LAST_ACCESS) val lastAccessMillis: Long = System.currentTimeMillis(),
    @ColumnInfo(name = COLUMN_DATE_CACHED) val cachedAtMillis: Long = System.currentTimeMillis()
) {

    companion object {
        const val TABLE_NAME = "profile"
        const val COLUMN_NAME = "name"
        const val COLUMN_AGE = "age"
        const val COLUMN_DATE_CACHED = "cached_at"
        const val COLUMN_LAST_ACCESS = "last_access"
    }
}