package id.deval.raport.db.models

import com.google.gson.annotations.SerializedName

data class Pesan(

	@field:SerializedName("siswaId")
	val siswaId: String? = null,

	@field:SerializedName("noteId")
	val noteId: List<String?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("growthId")
	val growthId: List<String?>? = null,

	@field:SerializedName("growthDetail")
	val growthDetail: List<Growth?>? = null,

	@field:SerializedName("noteDetail")
	val noteDetail: List<Note?>? = null
)
