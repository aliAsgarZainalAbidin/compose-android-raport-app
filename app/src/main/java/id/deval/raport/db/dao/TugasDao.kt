package id.deval.raport.db.dao

import androidx.room.*
import id.deval.raport.db.models.Tugas
import id.deval.raport.db.models.Siswa

@Dao
interface TugasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTugas(tugas : Tugas)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTugas(list : List<Tugas>)

    @Delete
    fun delete(tugas: Tugas)

    @Query("DELETE FROM Tugas")
    fun clearTableTugas()

    @Query("SELECT * FROM Tugas")
    fun getAllTugas() : List<Tugas>

    @Update(entity = Tugas::class, onConflict = OnConflictStrategy.REPLACE)
    fun updateTugas(tugas: Tugas)
}