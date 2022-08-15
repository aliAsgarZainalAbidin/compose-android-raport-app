package id.deval.raport.db.models.request

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UpdateSiswa(
	@field:SerializedName("siswaId")
	val listSiswa: ArrayList<String>? = null,
)
