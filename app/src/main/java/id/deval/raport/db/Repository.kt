package id.deval.raport.db

import id.deval.raport.db.models.Account
import id.deval.raport.db.models.AccountUpdate
import id.deval.raport.db.models.Siswa
import id.deval.raport.utils.wrappers.GlobalWrapper
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

    suspend fun getAllTeacher(token : String): GlobalWrapper<ArrayList<Account>>{
        return  apiInterface.getAccount("Bearer $token", "Guru")
    }

    suspend fun getAllSiswa(token: String): GlobalWrapper<ArrayList<Siswa>>{
        return apiInterface.getAllSiswa("Bearer $token")
    }

    suspend fun addAccountTeacher(token: String, account: Account): GlobalWrapper<Account>{
        return apiInterface.addAccountTeacher("Bearer $token", account)
    }

    suspend fun updateTeacher(token: String,id : String, account: AccountUpdate): GlobalWrapper<Account>{
        return apiInterface.updateAccountTeacher("Bearer $token",id, account)
    }

    suspend fun getTeacherById(token: String, id: String): GlobalWrapper<Account>{
        return apiInterface.getTeacherById("Bearer $token", id)
    }

    suspend fun deleteTeacherById(token: String, id:String) : Response<Unit>{
        return apiInterface.deleteTeacherById("Bearer $token", id)
    }
}