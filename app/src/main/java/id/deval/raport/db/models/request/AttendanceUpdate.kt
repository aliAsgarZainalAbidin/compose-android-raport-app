package id.deval.raport.db.models.request

import com.google.gson.annotations.SerializedName

data class AttendanceUpdate(
	@field:SerializedName("attendance")
	var attendance: List<AttendanceItem?>? = null
)

