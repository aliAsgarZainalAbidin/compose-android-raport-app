package id.deval.raport.db.models.request

import com.google.gson.annotations.SerializedName

data class KelasUpdate(

	@field:SerializedName("siswaId")
	val siswaId: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("mapelId")
	val mapelId: List<String?>? = null,

	@field:SerializedName("semester")
	val semester: Int? = null,

	@field:SerializedName("tahunAjaran")
	val tahunAjaran: String? = null,

	@field:SerializedName("guruKelas")
	val guruKelas: String? = null
)
