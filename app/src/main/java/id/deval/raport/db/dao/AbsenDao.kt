package id.deval.raport.db.dao

import androidx.room.*
import id.deval.raport.db.models.Absen
import id.deval.raport.db.models.Siswa

@Dao
interface AbsenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAbsen(absen : Absen)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAbsen(list : List<Absen>)

    @Delete
    fun delete(absen: Absen)

    @Query("DELETE FROM Absen")
    fun clearTableAbsen()

    @Query("SELECT * FROM Absen")
    fun getAllAbsen() : List<Absen>

    @Update(entity = Absen::class, onConflict = OnConflictStrategy.REPLACE)
    fun updateAbsen(absen: Absen)
}