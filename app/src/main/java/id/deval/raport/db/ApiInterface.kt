package id.deval.raport.db

import id.deval.raport.db.models.*
import id.deval.raport.db.models.request.*
import id.deval.raport.db.response.ResponseAttendance
import id.deval.raport.db.response.ResponseDetailKelas
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
    ): Response<GlobalWrapper<Account>>

    @GET("account/")
    suspend fun getAccount(
        @Header("Authorization") token: String,
        @Query("role") role: String,
    ): Response<GlobalWrapper<ArrayList<Account>>>

    @GET("siswa/")
    suspend fun getAllSiswa(
        @Header("Authorization") token: String,
    ): Response<GlobalWrapper<ArrayList<Siswa>>>

    @GET("siswa/{id}")
    suspend fun getSiswa(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<GlobalWrapper<SiswaUpdate>>

    @GET("siswa/{id}")
    suspend fun getSiswaById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<GlobalWrapper<Siswa>>

    @GET("siswa/nis/{id}")
    suspend fun getSiswaByNIS(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<GlobalWrapper<SiswaByNIS>>

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
        @Part image: MultipartBody.Part
    ): Response<GlobalWrapper<Siswa>>

    @Multipart
    @POST("siswa/updatePhoto/{id}")
    suspend fun uploadPhoto(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part image: MultipartBody.Part
    ): Response<GlobalWrapper<Siswa>>

    @PUT("siswa/{id}")
    suspend fun updateSiswa(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body siswa: Siswa
    ): Response<GlobalWrapper<Siswa>>

    @DELETE("siswa/{id}")
    suspend fun deleteSiswa(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Unit>

    @POST("account/")
    suspend fun addAccountTeacher(
        @Header("Authorization") token: String,
        @Body account: Account
    ): Response<GlobalWrapper<Account>>

    @PUT("account/{id}")
    suspend fun updateAccountTeacher(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body account: AccountUpdate
    ): Response<GlobalWrapper<Account>>

    @GET("account/{id}")
    suspend fun getTeacherById(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Response<GlobalWrapper<Account>>

    @DELETE("account/{id}")
    suspend fun deleteTeacherById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Unit>

    @GET("account/detail/{username}")
    suspend fun getAccountByUsername(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Response<GlobalWrapper<Account>>

    @DELETE("account/detail/{username}")
    suspend fun deleteAccountByUsername(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Response<Unit>

    @GET("class/")
    suspend fun getAllClass(
        @Header("Authorization") token: String
    ): Response<GlobalWrapper<ArrayList<Kelas>>>

    @POST("class/")
    suspend fun addClass(
        @Header("Authorization") token: String,
        @Body kelas: Kelas
    ): Response<GlobalWrapper<Kelas>>

    @DELETE("class/{id}")
    suspend fun deleteClass(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Unit>

    @GET("class/{id}")
    suspend fun getClassById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<GlobalWrapper<ArrayList<ResponseDetailKelas>>>

    @GET("class/guru/{id}")
    suspend fun getClassByIdGuru(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<GlobalWrapper<ResponseDetailKelas>>

    @GET("class/siswa/{id}")
    suspend fun getClassByNis(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<GlobalWrapper<ResponseDetailKelas>>

    @PUT("class/{id}")
    suspend fun updateClassById(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body kelas: KelasUpdate
    ): Response<GlobalWrapper<Kelas>>

    @PUT("class/{id}")
    suspend fun updateMapelInClassById(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body mapel: UpdateMapel
    ): Response<GlobalWrapper<Kelas>>

    @PUT("class/{id}")
    suspend fun updateSiswaInClassById(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body siswa: UpdateSiswa
    ): Response<GlobalWrapper<Kelas>>

    @GET("mapel/")
    suspend fun getAllMapel(
        @Header("Authorization") token: String
    ): Response<GlobalWrapper<ArrayList<Mapel>>>

    @POST("mapel/")
    suspend fun addMapel(
        @Header("Authorization") token: String,
        @Body mapel: MapelAdd
    ): Response<GlobalWrapper<Mapel>>

    @GET("mapel/{id}")
    suspend fun getMapelById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<GlobalWrapper<Mapel>>

    @DELETE("mapel/{id}")
    suspend fun deleteMapelById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Unit>

    @PUT("mapel/{id}")
    suspend fun updateMapelById(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body mapel: MapelAdd
    ): Response<GlobalWrapper<Mapel>>

    @POST("attendance/")
    suspend fun addAttendance(
        @Header("Authorization") token: String,
        @Body attendance: AttendanceAdd
    ): Response<GlobalWrapper<Attendance>>

    @GET("attendance/")
    suspend fun getAttendance(
        @Header("Authorization") token: String,
        @Query("classId") classId: String,
        @Query("mapelId") mapelId: String
    ): Response<GlobalWrapper<ArrayList<Attendance>>>

    @GET("attendance/orangtua")
    suspend fun getAttendanceBySiswaId(
        @Header("Authorization") token: String,
        @Query("siswaId") siswaId: String,
        @Query("mapelId") mapelId: String
    ): Response<GlobalWrapper<ArrayList<Attendance>>>

    @GET("attendance/{id}")
    suspend fun getAttendanceById(
        @Header("Authorization") token: String,
        @Path("id") id:String
    ): Response<GlobalWrapper<ArrayList<ResponseAttendance>>>

    @PUT("attendance/{id}")
    suspend fun updateAttendanceById(
        @Header("Authorization") token: String,
        @Path("id") id:String,
        @Body attendance: AttendanceAdd
    ): Response<GlobalWrapper<ResponseAttendance>>

    @GET("raport/detail")
    suspend fun getSpesifikRaport(
        @Header("Authorization") token:String,
        @Query("classId") classId:String,
        @Query("mapelId") mapelId:String,
        @Query("siswaId") siswaId:String,
    ): Response<GlobalWrapper<Raport>>

    @POST("tugas/")
    suspend fun addTugas(
        @Header("Authorization") token:String,
        @Body tugas: TugasAdd
    ): Response<GlobalWrapper<Raport>>

    @PUT("raport/")
    suspend fun updateRaport(
        @Header("Authorization") token:String,
        @Body raportAdd: RaportAdd,
    ): Response<GlobalWrapper<Raport>>

    @PUT("tugas/{id}")
    suspend fun updateTugasById(
        @Header("Authorization") token:String,
        @Path("id") id:String,
        @Body tugas: TugasAdd
    ) : Response<GlobalWrapper<Tugas>>

    @GET("tugas/{id}")
    suspend fun getTugasById(
        @Header("Authorization") token: String,
        @Path("id") id:String
    ) : Response<GlobalWrapper<Tugas>>

    @GET("pesan/")
    suspend fun getPesanById(
        @Header("Authorization") token:String,
        @Query("siswaId") siswaId: String
    ) : Response<GlobalWrapper<Pesan>>

    @POST("growth/")
    suspend fun addGrowth(
        @Header("Authorization") token: String,
        @Body growthAdd: GrowthAdd
    ) : Response<GlobalWrapper<Pesan>>

    @POST("note/")
    suspend fun addNote(
        @Header("Authorization") token: String,
        @Body noteAdd: NoteAdd
    ) : Response<GlobalWrapper<Pesan>>
}