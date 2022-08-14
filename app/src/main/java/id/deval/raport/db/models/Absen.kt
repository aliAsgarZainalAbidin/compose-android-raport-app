package id.deval.raport.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Absen(
    @field:SerializedName("kehadiran")
    val kehadiran: String? = null,

    @field:SerializedName("siswaId")
    @PrimaryKey
    val siswaId: String="",

    val nis:String? =null
)
