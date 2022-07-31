package id.deval.raport.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.deval.raport.db.models.ExampleEntity

@Dao
interface ExampleDao {
    @Query("SELECT * FROM ExampleEntity")
    fun getAllTvExampleEntity(): List<ExampleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ExampleEntity>)

    @Query("SELECT * FROM ExampleEntity WHERE id=:id")
    fun getTvExampleEntityById(id : String): ExampleEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data : ExampleEntity)

    @Query("DELETE FROM ExampleEntity WHERE id=:id")
    fun deleteTvExampleEntityById(id: String)
}