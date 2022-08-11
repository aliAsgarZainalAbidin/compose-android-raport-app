package id.deval.raport.db.dao

import androidx.room.*
import id.deval.raport.db.models.StringSiswa

@Dao
interface StringSiswaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSiswa(siswa : StringSiswa)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSiswa(list : List<StringSiswa>)

    @Delete
    fun delete(siswa: StringSiswa)

    @Query("SELECT * FROM StringSiswa")
    fun getAllStringSiswa() : List<StringSiswa>
}