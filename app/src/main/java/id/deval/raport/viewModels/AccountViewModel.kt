package id.deval.raport.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Account
import id.deval.raport.db.models.AccountUpdate
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private lateinit var mutableAccount : MutableLiveData<GlobalWrapper<ArrayList<Account>>>
    private lateinit var mutableAddTeacherResponse : MutableLiveData<GlobalWrapper<Account>>
    private lateinit var mutableUpdateTeacherResponse : MutableLiveData<GlobalWrapper<Account>>
    private lateinit var mutableGetTeacherIdResponse : MutableLiveData<GlobalWrapper<Account>>
    private lateinit var mutableDeleteTeacher : MutableLiveData<Response<Unit>>

    fun getAllTeacher(token : String) : LiveData<GlobalWrapper<ArrayList<Account>>>{
        mutableAccount = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAllTeacher(token)
            mutableAccount.postValue(response)
        }
        return mutableAccount
    }

    fun addTeacher(token : String, account: Account) : LiveData<GlobalWrapper<Account>>{
        mutableAddTeacherResponse = MutableLiveData()
        GlobalScope.launch {
            val response = repository.addAccountTeacher(token, account)
            mutableAddTeacherResponse.postValue(response)
        }

        return mutableAddTeacherResponse
    }

    fun updateTeacher(token: String, id :String, account: AccountUpdate): LiveData<GlobalWrapper<Account>>{
        mutableUpdateTeacherResponse = MutableLiveData()
        GlobalScope.launch {
            val response = repository.updateTeacher(token,id, account)
            mutableUpdateTeacherResponse.postValue(response)
        }
        return mutableUpdateTeacherResponse
    }

    fun getTeacherById(token:String, id:String): LiveData<GlobalWrapper<Account>>{
        mutableGetTeacherIdResponse = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getTeacherById(token, id)
            mutableGetTeacherIdResponse.postValue(response)
        }
        return mutableGetTeacherIdResponse
    }

    fun deleteTeacherById(token:String, id:String): LiveData<Response<Unit>>{
        mutableDeleteTeacher = MutableLiveData()
        GlobalScope.launch {
            val response = repository.deleteTeacherById(token, id)
            mutableDeleteTeacher.postValue(response)
        }
        return mutableDeleteTeacher
    }
}