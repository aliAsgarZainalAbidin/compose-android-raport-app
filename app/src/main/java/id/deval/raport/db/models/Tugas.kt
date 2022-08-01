package id.deval.raport.db.models

import com.google.gson.annotations.SerializedName

data class Tugas(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("nilai")
	val nilai: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null
)
