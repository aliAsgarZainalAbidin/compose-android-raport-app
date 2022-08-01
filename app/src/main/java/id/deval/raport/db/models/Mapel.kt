package id.deval.raport.db.models

import com.google.gson.annotations.SerializedName

data class Mapel(

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)
