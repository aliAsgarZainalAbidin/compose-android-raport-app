package id.deval.raport.db.dao

import androidx.room.*
import id.deval.raport.db.models.Mapel

@Dao
interface MapelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMapel(mapel : Mapel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMapel(list : List<Mapel>)

    @Delete
    fun delete(mapel: Mapel)

    @Query("DELETE FROM MAPEL")
    fun clearTableMapel()

    @Query("SELECT * FROM Mapel")
    fun getAllMapel() : List<Mapel>

}