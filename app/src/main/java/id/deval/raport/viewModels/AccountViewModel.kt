package id.deval.raport.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Account
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private lateinit var mutableAccount : MutableLiveData<GlobalWrapper<ArrayList<Account>>>

    fun getAllTeacher(token : String) : LiveData<GlobalWrapper<ArrayList<Account>>>{
        mutableAccount = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAllTeacher(token)
            mutableAccount.postValue(response)
        }
        return mutableAccount
    }
}