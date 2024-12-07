package mas.ca.humanprofiler.data.datasources.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import mas.ca.humanprofiler.data.datasources.local.entities.LocalProfile

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile WHERE name = :name")
    suspend fun getByName(name: String): LocalProfile?

    @Query("SELECT * FROM profile")
    suspend fun getAll(): List<LocalProfile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: LocalProfile)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(profile: LocalProfile)

    @Delete
    suspend fun delete(profile: LocalProfile)


}