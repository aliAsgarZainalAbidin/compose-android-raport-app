package id.deval.raport.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Kelas
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class KelasViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){

    private lateinit var mutableAllClass : MutableLiveData<GlobalWrapper<ArrayList<Kelas>>>
    private lateinit var mutableAddClass : MutableLiveData<GlobalWrapper<Kelas>>
    private lateinit var mutableDeleteClass : MutableLiveData<Response<Unit>>

    fun getAllClass(token: String): LiveData<GlobalWrapper<ArrayList<Kelas>>>{
        mutableAllClass = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAllClass(token)
            mutableAllClass.postValue(response)
        }
        return mutableAllClass
    }

    fun addClass(token: String, kelas: Kelas): LiveData<GlobalWrapper<Kelas>>{
        mutableAddClass = MutableLiveData()
        GlobalScope.launch {
            val response = repository.addClass(token, kelas)
            mutableAddClass.postValue(response)
        }
        return mutableAddClass
    }
    fun deleteClass(token: String, id: String): LiveData<Response<Unit>>{
        mutableDeleteClass = MutableLiveData()
        GlobalScope.launch {
            val response = repository.deleteClass(token, id)
            mutableDeleteClass.postValue(response)
        }
        return mutableDeleteClass
    }
}