package id.deval.raport.db.response

import com.google.gson.annotations.SerializedName
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa

data class ResponseDetailKelas(

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("detailGuru")
	val detailGuru: List<DetailGuruItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("siswaDetail")
	val siswaDetail: ArrayList<Siswa?>? = null,

	@field:SerializedName("siswaId")
	val siswaId: ArrayList<String?>? = null,

	@field:SerializedName("semester")
	val semester: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("tahunAjaran")
	val tahunAjaran: String? = null,

	@field:SerializedName("mapelDetail")
	val mapelDetail: ArrayList<Mapel?>? = null
)

data class MapelDetailItem(

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)

data class DetailGuruItem(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("classId")
	val classId: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class SiswaDetailItem(

	@field:SerializedName("education")
	val education: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("pekerjaanWali")
	val pekerjaanWali: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("alamatWali")
	val alamatWali: String? = null,

	@field:SerializedName("religion")
	val religion: String? = null,

	@field:SerializedName("namaWali")
	val namaWali: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("nis")
	val nis: String? = null,

	@field:SerializedName("pekerjaanAyah")
	val pekerjaanAyah: String? = null,

	@field:SerializedName("pekerjaanIbu")
	val pekerjaanIbu: String? = null,

	@field:SerializedName("_id")
	val id: String? = null
)
