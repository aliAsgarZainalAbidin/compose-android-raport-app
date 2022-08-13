package id.deval.raport.db.models.request

import com.google.gson.annotations.SerializedName

data class NoteAdd(

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("pesanId")
	val pesanId: String? = null
)
