package id.deval.raport.db.dao

import androidx.room.*
import id.deval.raport.db.models.StringMapel

@Dao
interface StringMapelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMapel(mapel : StringMapel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMapel(list : List<StringMapel>)

    @Delete
    fun delete(mapel: StringMapel)

    @Query("SELECT * FROM StringMapel")
    fun getAllStringMapel() : List<StringMapel>
}