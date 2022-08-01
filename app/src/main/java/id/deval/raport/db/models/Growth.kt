package id.deval.raport.db.models

import com.google.gson.annotations.SerializedName

data class Growth(

	@field:SerializedName("berat")
	val berat: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("ket")
	val ket: String? = null,

	@field:SerializedName("tinggi")
	val tinggi: Int? = null,

	@field:SerializedName("bmi")
	val bmi: Int? = null
)
