package id.deval.raport.db.models.request

import com.google.gson.annotations.SerializedName
import id.deval.raport.db.models.Tugas

data class RaportAdd(

	@field:SerializedName("tugasId")
	val tugasId: List<String?>? = null,

	@field:SerializedName("nilaiSikap")
	val nilaiSikap: Int? = null,

	@field:SerializedName("nilaiUTS")
	val nilaiUTS: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("nilaiUAS")
	val nilaiUAS: Int? = null,

	@field:SerializedName("classId")
	val classId: String? = null,

	@field:SerializedName("siswaId")
	val siswaId: String? = null,

	@field:SerializedName("mapelId")
	val mapelId: String? = null,
)
