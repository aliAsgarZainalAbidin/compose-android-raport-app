package id.deval.raport.db

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    val database: Database,
    val apiInterface: ApiInterface
) {
//    suspend fun login(loginRequest: LoginRequest): GlobalWrapperResponse<UserResponseObject<User?>> {
//        val loginInformation = apiInterface.login(loginRequest)
//        return loginInformation
//    }
}