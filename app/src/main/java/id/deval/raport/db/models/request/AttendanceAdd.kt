package id.deval.raport.db.models.request

import com.google.gson.annotations.SerializedName

data class AttendanceAdd(

	@field:SerializedName("classId")
	var classId: String? = null,

	@field:SerializedName("mapelId")
	var mapelId: String? = null,

	@field:SerializedName("tanggalAbsen")
	var tanggalAbsen: String? = null,

	@field:SerializedName("attendance")
	var attendance: List<AttendanceItem?>? = null
)

data class AttendanceItem(

	@field:SerializedName("kehadiran")
	var kehadiran: String? = null,

	@field:SerializedName("siswaId")
	var siswaId: String? = null,

)
