package id.deval.raport.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.BuildConfig.TAG
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Account
import id.deval.raport.db.models.request.AccountUpdate
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private lateinit var mutableAccount: MutableLiveData<Response<GlobalWrapper<ArrayList<Account>>>>
    private lateinit var mutableAddTeacherResponse: MutableLiveData<Response<GlobalWrapper<Account>>>
    private lateinit var mutableSignup: MutableLiveData<Response<GlobalWrapper<Account>>>
    private lateinit var mutableUpdateTeacherResponse: MutableLiveData<Response<GlobalWrapper<Account>>>
    private lateinit var mutableGetTeacherIdResponse: MutableLiveData<Response<GlobalWrapper<Account>>>
    private lateinit var mutableDeleteTeacher: MutableLiveData<Response<Unit>>
    private lateinit var mutableDeleteAccount: MutableLiveData<Response<Unit>>
    private lateinit var mutableDetailAccount: MutableLiveData<Response<GlobalWrapper<Account>>>

    fun getAllTeacher(token: String): LiveData<Response<GlobalWrapper<ArrayList<Account>>>> {
        mutableAccount = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAllTeacher(token)
            mutableAccount.postValue(response)
        }
        return mutableAccount
    }

    fun addTeacher(token: String, account: Account): LiveData<Response<GlobalWrapper<Account>>> {
        mutableAddTeacherResponse = MutableLiveData()
        GlobalScope.launch {
            try {
                val response = repository.addAccountTeacher(token, account)
                mutableAddTeacherResponse.postValue(response)
            } catch (e: Exception) {
                Log.d(TAG, "addTeacher: $e")
            }
        }
        return mutableAddTeacherResponse
    }


    fun signup(account: Account): LiveData<Response<GlobalWrapper<Account>>> {
        mutableSignup = MutableLiveData()
        GlobalScope.launch {
            try {
                val response = repository.signup(account)
                mutableSignup.postValue(response)
            } catch (e: Exception) {
                Log.d(TAG, "addTeacher: $e")
            }
        }
        return mutableSignup
    }

    fun updateTeacher(
        token: String,
        id: String,
        account: AccountUpdate
    ): LiveData<Response<GlobalWrapper<Account>>> {
        mutableUpdateTeacherResponse = MutableLiveData()
        GlobalScope.launch {
            val response = repository.updateTeacher(token, id, account)
            mutableUpdateTeacherResponse.postValue(response)
        }
        return mutableUpdateTeacherResponse
    }

    fun getTeacherById(token: String, id: String): LiveData<Response<GlobalWrapper<Account>>> {
        mutableGetTeacherIdResponse = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getTeacherById(token, id)
            mutableGetTeacherIdResponse.postValue(response)
        }
        return mutableGetTeacherIdResponse
    }

    fun deleteTeacherById(token: String, id: String): LiveData<Response<Unit>> {
        mutableDeleteTeacher = MutableLiveData()
        GlobalScope.launch {
            val response = repository.deleteTeacherById(token, id)
            mutableDeleteTeacher.postValue(response)
        }
        return mutableDeleteTeacher
    }

    fun deleteAccountByUsername(token: String, username: String): LiveData<Response<Unit>> {
        mutableDeleteAccount = MutableLiveData()
        GlobalScope.launch {
            val response = repository.deleteAccountByUsername(token, username)
            mutableDeleteAccount.postValue(response)
        }
        return mutableDeleteAccount
    }

    fun getAccountByUsername(
        token: String,
        username: String
    ): LiveData<Response<GlobalWrapper<Account>>> {
        mutableDetailAccount = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAccountByUsername(token, username)
            mutableDetailAccount.postValue(response)
        }
        return mutableDetailAccount
    }
}