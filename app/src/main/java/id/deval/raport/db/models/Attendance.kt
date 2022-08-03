package id.deval.raport.db.models

import com.google.gson.annotations.SerializedName

data class Attendance(

	@field:SerializedName("_id")
	val id: String? = null,

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
