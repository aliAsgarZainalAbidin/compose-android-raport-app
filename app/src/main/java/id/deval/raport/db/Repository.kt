package id.deval.raport.db

import id.deval.raport.db.models.*
import id.deval.raport.db.models.request.AccountUpdate
import id.deval.raport.db.models.request.SiswaUpdate
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    val database: Database,
    val apiInterface: ApiInterface
) {
    suspend fun login(account: Account): GlobalWrapper<Account> {
        return apiInterface.login(account)
    }

    suspend fun getAllTeacher(token: String): GlobalWrapper<ArrayList<Account>> {
        return apiInterface.getAccount("Bearer $token", "Guru")
    }

    suspend fun getAllSiswa(token: String): GlobalWrapper<ArrayList<Siswa>> {
        return apiInterface.getAllSiswa("Bearer $token")
    }

    suspend fun getSiswa(token: String, id:String): GlobalWrapper<SiswaUpdate>{
        return apiInterface.getSiswa("Bearer $token", id)
    }

    suspend fun addSiswa(
        token: String,
        name: MultipartBody.Part,
        nis: MultipartBody.Part,
        tempatLahir: MultipartBody.Part,
        tanggalLahir: MultipartBody.Part,
        address: MultipartBody.Part,
        education: MultipartBody.Part,
        religion: MultipartBody.Part,
        gender: MultipartBody.Part,
        namaAyah: MultipartBody.Part,
        namaIbu: MultipartBody.Part,
        pekerjaanAyah: MultipartBody.Part,
        pekerjaanIbu: MultipartBody.Part,
        namaWali: MultipartBody.Part,
        pekerjaanWali: MultipartBody.Part,
        alamatWali: MultipartBody.Part,
        phone: MultipartBody.Part,
        photo: MultipartBody.Part
    ): GlobalWrapper<Siswa> {
        return apiInterface.addSiswa(
            "Bearer $token",
            name,
            nis,
            tempatLahir,
            tanggalLahir,
            address,
            education,
            religion,
            gender,
            namaAyah,
            namaIbu,
            pekerjaanAyah,
            pekerjaanIbu,
            namaWali,
            pekerjaanWali,
            alamatWali,
            phone,
            photo
        )
    }

    suspend fun updateSiswa(token: String, id: String, siswa: Siswa) : GlobalWrapper<Siswa>{
        return apiInterface.updateSiswa("Bearer $token", id, siswa)
    }

    suspend fun deleteSiswaById(token: String, id: String): Response<Unit>{
        return apiInterface.deleteSiswa("Bearer $token", id)
    }

    suspend fun uploadPhoto(token: String, id: String, photo: MultipartBody.Part) : GlobalWrapper<Siswa>{
        return apiInterface.uploadPhoto("Bearer $token", id, photo)
    }

    suspend fun addAccountTeacher(token: String, account: Account): GlobalWrapper<Account> {
        return apiInterface.addAccountTeacher("Bearer $token", account)
    }

    suspend fun updateTeacher(
        token: String,
        id: String,
        account: AccountUpdate
    ): GlobalWrapper<Account> {
        return apiInterface.updateAccountTeacher("Bearer $token", id, account)
    }

    suspend fun getTeacherById(token: String, id: String): GlobalWrapper<Account> {
        return apiInterface.getTeacherById("Bearer $token", id)
    }

    suspend fun deleteTeacherById(token: String, id: String): Response<Unit> {
        return apiInterface.deleteTeacherById("Bearer $token", id)
    }

    suspend fun getAccountByUsername(token: String, username: String): GlobalWrapper<Account>{
        return apiInterface.getAccountByUsername("Bearer $token", username)
    }

    suspend fun deleteAccountByUsername(token: String, username: String): Response<Unit> {
        return apiInterface.deleteAccountByUsername("Bearer $token", username)
    }

    suspend fun getAllClass(token: String): GlobalWrapper<ArrayList<Kelas>>{
        return apiInterface.getAllClass("Bearer $token")
    }

    suspend fun getAllMapel(token: String): GlobalWrapper<ArrayList<Mapel>>{
        return apiInterface.getAllMapel("Bearer $token")
    }

    suspend fun insertSiswa(siswa: StringSiswa){
        withContext(Dispatchers.IO){
            database.stringSiswaDao().addSiswa(siswa)
        }
    }

    suspend fun insertAllSiswa(siswa: List<StringSiswa>){
        withContext(Dispatchers.IO){
            database.stringSiswaDao().insertAllSiswa(siswa)
        }
    }

    suspend fun deleteSiswa(siswa: StringSiswa){
        withContext(Dispatchers.IO){
            database.stringSiswaDao().delete(siswa)
        }
    }

    fun getAllSiswa():List<StringSiswa>{
        return database.stringSiswaDao().getAllStringSiswa()
    }

    suspend fun insertMapel(mapel: StringMapel){
        withContext(Dispatchers.IO){
            database.stringMapelDao().addMapel(mapel)
        }
    }

    suspend fun insertAllMapel(mapel: List<StringMapel>){
        withContext(Dispatchers.IO){
            database.stringMapelDao().insertAllMapel(mapel)
        }
    }

    suspend fun deleteMapel(mapel: StringMapel){
        withContext(Dispatchers.IO){
            database.stringMapelDao().delete(mapel)
        }
    }

    fun getAllMapel():List<StringMapel>{
        return database.stringMapelDao().getAllStringMapel()
    }
}