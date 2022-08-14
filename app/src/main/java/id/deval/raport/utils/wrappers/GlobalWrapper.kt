package id.deval.raport.utils.wrappers

import com.google.gson.annotations.SerializedName
import id.deval.raport.db.models.ResponseError

data class GlobalWrapper<T>(
    @field:SerializedName("status")
    val status: String?,

    @field:SerializedName("token")
    val token: String?,

    @field:SerializedName("message")
    val message: String?,

    @field:SerializedName("data")
    val data: T,

    @field:SerializedName("result")
    val result: Int?
)
