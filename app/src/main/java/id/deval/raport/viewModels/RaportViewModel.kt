package id.deval.raport.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Tugas
import id.deval.raport.db.models.Raport
import id.deval.raport.db.models.request.RaportAdd
import id.deval.raport.db.models.request.TugasAdd
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RaportViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private lateinit var mutableRaport : MutableLiveData<Response<GlobalWrapper<Raport>>>
    private lateinit var mutableUpdateRaport : MutableLiveData<Response<GlobalWrapper<Raport>>>
    private lateinit var mutableAddTugas : MutableLiveData<Response<GlobalWrapper<Raport>>>
    private lateinit var mutableUpdateTugas : MutableLiveData<Response<GlobalWrapper<Tugas>>>
    private lateinit var mutableGetTugasById : MutableLiveData<Response<GlobalWrapper<Tugas>>>
    private lateinit var mutableGetLocalTugas : MutableLiveData<List<Tugas>>

    fun getSpesifikRaport(token:String, classId:String, mapelId:String, siswaId:String) : LiveData<Response<GlobalWrapper<Raport>>>{
        mutableRaport = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getSpesifikRaport(token, classId, mapelId, siswaId)
            mutableRaport.postValue(response)
        }
        return mutableRaport
    }

    fun addTugas(token:String, tugas: TugasAdd) : LiveData<Response<GlobalWrapper<Raport>>>{
        mutableRaport = MutableLiveData()
        GlobalScope.launch {
            val response = repository.addTugas(token, tugas)
            mutableRaport.postValue(response)
        }
        return mutableRaport
    }

    fun updateRaport(token:String, raportAdd: RaportAdd) : LiveData<Response<GlobalWrapper<Raport>>>{
        mutableUpdateRaport = MutableLiveData()
        GlobalScope.launch {
            val response = repository.updateRaport(token, raportAdd)
            mutableUpdateRaport.postValue(response)
        }
        return mutableUpdateRaport
    }

    fun updateTugasById(token:String, id:String, tugas: TugasAdd) : LiveData<Response<GlobalWrapper<Tugas>>>{
        mutableUpdateTugas = MutableLiveData()
        GlobalScope.launch {
            val response = repository.updateTugasById(token, id, tugas)
            mutableUpdateTugas.postValue(response)
        }
        return mutableUpdateTugas
    }

    fun getTugasById(token:String, id:String) : LiveData<Response<GlobalWrapper<Tugas>>>{
        mutableGetTugasById = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getTugasById(token, id)
            mutableGetTugasById.postValue(response)
        }
        return mutableGetTugasById
    }

    fun insertLocalTugas(tugas: Tugas){
        GlobalScope.launch {
            repository.insertTugas(tugas)
        }
    }

    fun updateLocalTugas(tugas: Tugas){
        GlobalScope.launch {
            repository.updateTugas(tugas)
        }
    }

    fun insertAllLocalTugas(list : ArrayList<Tugas>){
        GlobalScope.launch {
            repository.insertAllTugas(list)
        }
    }

    fun deleteLocalTugas(tugas: Tugas){
        GlobalScope.launch {
            repository.deleteTugas(tugas)
        }
    }
    fun clearTableTugas(){
        GlobalScope.launch {
            repository.clearTableTugas()
        }
    }

    fun getAllLocalTugas():MutableLiveData<List<Tugas>>{
        mutableGetLocalTugas = MutableLiveData()
        GlobalScope.launch {
            mutableGetLocalTugas.postValue(repository.getAllTugas())
        }
        return mutableGetLocalTugas
    }
}