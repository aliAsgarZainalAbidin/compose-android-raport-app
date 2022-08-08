package id.deval.raport.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Account
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private lateinit var mutableUserLogin : MutableLiveData<Account>
    private lateinit var mutableUserLogout : MutableLiveData<Account>
    var token : String? = null

    fun login(account: Account): LiveData<Account> {
        mutableUserLogin = MutableLiveData()
        GlobalScope.launch {
            val response = repository.login(account)
            token = response.token
            if (response.status?.lowercase().equals("success")){
                mutableUserLogin.postValue(response.data)
            } else {
                mutableUserLogin.postValue(null)
            }
        }
        return mutableUserLogin
    }
}