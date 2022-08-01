package id.deval.raport.db.models

import com.google.gson.annotations.SerializedName

data class Siswa(

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("education")
	val education: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("pekerjaanWali")
	val pekerjaanWali: String? = null,

	@field:SerializedName("tanggalLahir")
	val tanggalLahir: String? = null,

	@field:SerializedName("alamatWali")
	val alamatWali: String? = null,

	@field:SerializedName("religion")
	val religion: String? = null,

	@field:SerializedName("namaAyah")
	val namaAyah: String? = null,

	@field:SerializedName("classId")
	val classId: String? = null,

	@field:SerializedName("namaWali")
	val namaWali: String? = null,

	@field:SerializedName("namaIbu")
	val namaIbu: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("nis")
	val nis: String? = null,

	@field:SerializedName("tempatLahir")
	val tempatLahir: String? = null,

	@field:SerializedName("pekerjaanAyah")
	val pekerjaanAyah: String? = null,

	@field:SerializedName("pekerjaanIbu")
	val pekerjaanIbu: String? = null
)
