package id.deval.raport.db.models.request

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UpdateMapel(
	@field:SerializedName("mapelId")
	val listMapel: ArrayList<String>? = null,
)
