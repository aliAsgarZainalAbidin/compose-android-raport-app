package id.deval.raport.db

import id.deval.raport.db.models.Account
import id.deval.raport.db.models.Siswa
import id.deval.raport.utils.wrappers.GlobalWrapper
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
}