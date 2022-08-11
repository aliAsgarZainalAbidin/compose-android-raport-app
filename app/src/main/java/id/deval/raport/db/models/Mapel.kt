package id.deval.raport.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Mapel(

	@PrimaryKey
	@field:SerializedName("_id")
	val id: String="",

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)
