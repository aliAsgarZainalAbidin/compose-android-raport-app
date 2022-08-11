package id.deval.raport.db

import androidx.room.Database
import androidx.room.RoomDatabase
import id.deval.raport.db.dao.ExampleDao
import id.deval.raport.db.dao.StringMapelDao
import id.deval.raport.db.dao.StringSiswaDao
import id.deval.raport.db.models.ExampleEntity
import id.deval.raport.db.models.StringMapel
import id.deval.raport.db.models.StringSiswa

@Database(entities = [ExampleEntity::class, StringSiswa::class, StringMapel::class], version = 1, exportSchema = false)
abstract class Database() : RoomDatabase(){
    abstract fun exampleDao(): ExampleDao
    abstract fun stringSiswaDao(): StringSiswaDao
    abstract fun stringMapelDao():StringMapelDao
}
