package id.deval.raport.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

//    private lateinit var mutableUserLogin : MutableLiveData<User>
//    private lateinit var mutableUserLogout : MutableLiveData<User>
//
//    fun login(loginRequest: LoginRequest): LiveData<User> {
//        mutableUserLogin = MutableLiveData()
//        GlobalScope.launch {
//            val data = repository.login(loginRequest)
//            if (data.status.equals("success")){
//                mutableUserLogin.postValue(data.data.user)
//            } else {
//                mutableUserLogin.postValue(null)
//            }
//        }
//        return mutableUserLogin
//    }
}