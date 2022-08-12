package id.deval.raport.db.models.request

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MapelAdd(
	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)
