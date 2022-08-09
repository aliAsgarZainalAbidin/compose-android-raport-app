package id.deval.raport.db

import id.deval.raport.db.models.Account
import id.deval.raport.db.models.request.AccountUpdate
import id.deval.raport.db.models.Siswa
import id.deval.raport.utils.wrappers.GlobalWrapper
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

    @POST("siswa/")
    suspend fun addSiswa(
        @Header("Authorization") token: String,
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
}