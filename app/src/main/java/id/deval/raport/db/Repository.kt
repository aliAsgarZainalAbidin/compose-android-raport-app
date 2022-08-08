package id.deval.raport.db

import id.deval.raport.db.models.Account
import id.deval.raport.utils.wrappers.GlobalWrapper
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
}