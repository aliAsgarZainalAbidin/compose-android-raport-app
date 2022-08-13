package id.deval.raport.db

import androidx.room.Database
import androidx.room.RoomDatabase
import id.deval.raport.db.dao.*
import id.deval.raport.db.models.*

@Database(entities = [ExampleEntity::class, Siswa::class, Mapel::class, Tugas::class, Absen::class], version = 1, exportSchema = false)
abstract class Database() : RoomDatabase(){
    abstract fun exampleDao(): ExampleDao
    abstract fun siswaDao(): SiswaDao
    abstract fun mapelDao():MapelDao
    abstract fun absenDao():AbsenDao
    abstract fun tugasDao():TugasDao
}
