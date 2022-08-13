package id.deval.raport.db.models.request

import com.google.gson.annotations.SerializedName

data class GrowthAdd(

	@field:SerializedName("berat")
	val berat: Int? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("ket")
	val ket: String? = null,

	@field:SerializedName("tinggi")
	val tinggi: Int? = null,

	@field:SerializedName("bmi")
	val bmi: Int? = null,

	@field:SerializedName("pesanId")
	val pesanId: String? = null,
)
