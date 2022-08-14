package id.deval.raport.db.models

import com.google.gson.annotations.SerializedName

data class ResponseError(

	@field:SerializedName("isOperational")
	val isOperational: Boolean? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
