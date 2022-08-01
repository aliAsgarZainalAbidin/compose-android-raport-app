package id.deval.raport.db.models

import com.google.gson.annotations.SerializedName

data class Pesan(

	@field:SerializedName("siswaId")
	val siswaId: String? = null,

	@field:SerializedName("noteId")
	val noteId: List<Note?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("growthId")
	val growthId: List<Growth?>? = null
)
