package id.deval.raport.db

import androidx.room.Database
import androidx.room.RoomDatabase
import id.deval.raport.db.dao.ExampleDao
import id.deval.raport.db.models.ExampleEntity

@Database(entities = [ExampleEntity::class], version = 1, exportSchema = false)
abstract class Database() : RoomDatabase(){
    abstract fun exampleDao(): ExampleDao
}
