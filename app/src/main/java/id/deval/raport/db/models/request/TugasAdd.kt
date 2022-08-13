package id.deval.raport.db.models.request

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TugasAdd(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("nilai")
	val nilai: Int? = null,

	@field:SerializedName("raportId")
	val raportId: String? = null,
)
