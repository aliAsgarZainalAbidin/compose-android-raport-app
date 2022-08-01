package id.deval.raport.db.models

import com.google.gson.annotations.SerializedName

data class Note(

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null
)
