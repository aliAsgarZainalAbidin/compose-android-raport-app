package id.deval.raport.db.dao

import androidx.room.*
import id.deval.raport.db.models.Siswa

@Dao
interface SiswaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSiswa(siswa : Siswa)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSiswa(list : List<Siswa>)

    @Delete
    fun delete(siswa: Siswa)

    @Query("SELECT * FROM Siswa")
    fun getAllSiswa() : List<Siswa>
}