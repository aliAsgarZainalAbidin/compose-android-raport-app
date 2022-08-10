package id.deval.raport.db

import id.deval.raport.db.models.Account
import id.deval.raport.db.models.request.AccountUpdate
import id.deval.raport.db.models.Siswa
import id.deval.raport.db.models.request.SiswaUpdate
import id.deval.raport.utils.wrappers.GlobalWrapper
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
//    @DELETE("rayon/{id}")
//    suspend fun deleteRayonById(
//        @Header("token") token: String,
//        @Path("id") id:String
//    ):MessageResponse
//
//    @GET("rayon/{id}")
//    suspend fun getRayonById(
//        @Header("token") token: String,
//        @Path("id") id: String
//    ): GlobalWrapperResponse<RayonWrapper<RayonRequest>>
//
//    @PUT("rayon/{id}")
//    suspend fun updateRayonById(
//        @Body rayonRequest: RayonRequest,
//        @Header("token") token: String,
//        @Path("id") id: String
//    ): RayonRequest
//
//    @POST("sinder")
//    suspend fun addSinder(
//        @Body sinderRequest: SinderRequest, @Header("token") token: String
//    ): MessageResponse

    @POST("account/login")
    suspend fun login(
        @Body account: Account
    ) : GlobalWrapper<Account>

    @GET("account/")
    suspend fun getAccount(
        @Header("Authorization") token:String,
        @Query("role") role: String,
    ) : GlobalWrapper<ArrayList<Account>>

    @GET("siswa/")
    suspend fun getAllSiswa(
        @Header("Authorization") token: String,
    ) : GlobalWrapper<ArrayList<Siswa>>

    @GET("siswa/{id}")
    suspend fun getSiswa(
        @Header("Authorization") token: String,
        @Path("id") id:String
    ) : GlobalWrapper<SiswaUpdate>

    @Multipart
    @POST("siswa/")
    suspend fun addSiswa(
        @Header("Authorization") token: String,
        @Part name: MultipartBody.Part,
        @Part nis: MultipartBody.Part,
        @Part tempatLahir: MultipartBody.Part,
        @Part tanggalLahir: MultipartBody.Part,
        @Part address: MultipartBody.Part,
        @Part education: MultipartBody.Part,
        @Part religion: MultipartBody.Part,
        @Part gender: MultipartBody.Part,
        @Part namaAyah: MultipartBody.Part,
        @Part namaIbu: MultipartBody.Part,
        @Part pekerjaanAyah: MultipartBody.Part,
        @Part pekerjaanIbu: MultipartBody.Part,
        @Part namaWali: MultipartBody.Part,
        @Part pekerjaanWali: MultipartBody.Part,
        @Part alamatWali: MultipartBody.Part,
        @Part phone: MultipartBody.Part,
        @Part image : MultipartBody.Part
    ) : GlobalWrapper<Siswa>

    @Multipart
    @POST("siswa/updatePhoto/{id}")
    suspend fun uploadPhoto(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part image: MultipartBody.Part
    ) : GlobalWrapper<Siswa>

    @PUT("siswa/{id}")
    suspend fun updateSiswa(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body siswa: Siswa
    ) : GlobalWrapper<Siswa>

    @POST("account/")
    suspend fun addAccountTeacher(
        @Header("Authorization") token: String,
        @Body account: Account
    ) : GlobalWrapper<Account>

    @PUT("account/{id}")
    suspend fun updateAccountTeacher(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body account: AccountUpdate
    ) : GlobalWrapper<Account>

    @GET("account/{id}")
    suspend fun getTeacherById(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ) : GlobalWrapper<Account>

    @DELETE("account/{id}")
    suspend fun deleteTeacherById(
        @Header("Authorization") token:String,
        @Path("id") id:String
    ) : Response<Unit>

    @GET("account/detail/{username}")
    suspend fun getAccountByUsername(
        @Header("Authorization") token: String,
        @Path("username") username:String
    ) : GlobalWrapper<Account>
}