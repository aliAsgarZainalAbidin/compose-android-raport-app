package id.deval.raport.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Tugas
import id.deval.raport.db.models.Raport
import id.deval.raport.db.models.request.TugasAdd
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaportViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private lateinit var mutableRaport : MutableLiveData<GlobalWrapper<Raport>>
    private lateinit var mutableAddTugas : MutableLiveData<GlobalWrapper<Raport>>
    private lateinit var mutableGetLocalTugas : MutableLiveData<List<Tugas>>

    fun getSpesifikRaport(token:String, classId:String, mapelId:String, siswaId:String) : LiveData<GlobalWrapper<Raport>>{
        mutableRaport = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getSpesifikRaport(token, classId, mapelId, siswaId)
            mutableRaport.postValue(response)
        }
        return mutableRaport
    }

    fun addTugas(token:String, tugas: TugasAdd) : LiveData<GlobalWrapper<Raport>>{
        mutableRaport = MutableLiveData()
        GlobalScope.launch {
            val response = repository.addTugas(token, tugas)
            mutableRaport.postValue(response)
        }
        return mutableRaport
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