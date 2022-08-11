package id.deval.raport.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StringSiswa(
    @PrimaryKey
    val id:String
)
