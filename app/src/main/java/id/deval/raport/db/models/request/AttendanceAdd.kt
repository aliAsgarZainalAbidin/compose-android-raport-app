package id.deval.raport.db.models.request

import com.google.gson.annotations.SerializedName

data class AttendanceAdd(

	@field:SerializedName("classId")
	val classId: String? = null,

	@field:SerializedName("mapelId")
	val mapelId: String? = null,

	@field:SerializedName("tanggalAbsen")
	val tanggalAbsen: String? = null,

	@field:SerializedName("attendance")
	val attendance: List<AttendanceItem?>? = null
)

data class AttendanceItem(

	@field:SerializedName("kehadiran")
	val kehadiran: String? = null,

	@field:SerializedName("siswaId")
	val siswaId: String? = null,

)
