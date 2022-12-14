package id.deval.raport.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Tugas(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("nilai")
	val nilai: Int? = null,

	@field:SerializedName("_id")
	@PrimaryKey
	val id: String=""
)
