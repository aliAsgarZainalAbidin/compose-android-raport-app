package id.deval.raport.db.response

import com.google.gson.annotations.SerializedName

data class ResponseAttendance(

	@field:SerializedName("classId")
	val classId: String? = null,

	@field:SerializedName("detailSiswa")
	val detailSiswa: List<DetailSiswaItem?>? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("mapelId")
	val mapelId: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

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

	@field:SerializedName("_id")
	val id: String? = null
)

data class DetailSiswaItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("nis")
	val nis: String? = null,

	@field:SerializedName("kehadiran")
	val kehadiran: String? = null,
)
