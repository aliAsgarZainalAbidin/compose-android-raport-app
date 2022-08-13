package id.deval.raport.db.models

import com.google.gson.annotations.SerializedName

data class Raport(

	@field:SerializedName("tugasId")
	val tugasId: List<String?>? = null,

	@field:SerializedName("nilaiSikap")
	val nilaiSikap: Int? = null,

	@field:SerializedName("classId")
	val classId: String? = null,

	@field:SerializedName("siswaId")
	val siswaId: String? = null,

	@field:SerializedName("mapelId")
	val mapelId: String? = null,

	@field:SerializedName("nilaiUTS")
	val nilaiUTS: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("nilaiUAS")
	val nilaiUAS: Int? = null,

	@field:SerializedName("tugasDetail")
	val tugasDetail: List<Tugas?>? = null
)
