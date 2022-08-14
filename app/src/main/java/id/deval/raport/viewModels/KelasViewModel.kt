package id.deval.raport.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Kelas
import id.deval.raport.db.models.request.KelasUpdate
import id.deval.raport.db.response.ResponseDetailKelas
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
    private lateinit var mutableUpdateClass : MutableLiveData<GlobalWrapper<Kelas>>
    private lateinit var mutableDeleteClass : MutableLiveData<Response<Unit>>
    private lateinit var mutableClassById : MutableLiveData<GlobalWrapper<ArrayList<ResponseDetailKelas>>>
    private lateinit var mutableClassByIdGuru : MutableLiveData<GlobalWrapper<ResponseDetailKelas>>
    private lateinit var mutableClassByNis : MutableLiveData<GlobalWrapper<ResponseDetailKelas>>

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

    fun getClassById(token: String, id: String): LiveData<GlobalWrapper<ArrayList<ResponseDetailKelas>>>{
        mutableClassById = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getClassById(token, id)
            mutableClassById.postValue(response)
        }
        return mutableClassById
    }

    fun getClassByIdGuru(token: String, id: String): LiveData<GlobalWrapper<ResponseDetailKelas>>{
        mutableClassByIdGuru = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getClassByIdGuru(token, id)
            mutableClassByIdGuru.postValue(response)
        }
        return mutableClassByIdGuru
    }

    fun getClassByNis(token: String, id: String): LiveData<GlobalWrapper<ResponseDetailKelas>>{
        mutableClassByNis = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getClassByNis(token, id)
            mutableClassByNis.postValue(response)
        }
        return mutableClassByNis
    }

    fun updateClassById(token: String, id: String, kelas: KelasUpdate): LiveData<GlobalWrapper<Kelas>>{
        mutableUpdateClass = MutableLiveData()
        GlobalScope.launch {
            val response = repository.updateClassById(token, id, kelas)
            mutableUpdateClass.postValue(response)
        }
        return mutableUpdateClass
    }
}