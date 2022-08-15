package id.deval.raport.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Attendance
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.db.models.request.MapelAdd
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MapelViewModel @Inject constructor(
  private val repository: Repository
) : ViewModel() {
    private lateinit var mutableAllMapel : MutableLiveData<Response<GlobalWrapper<ArrayList<Mapel>>>>
    private lateinit var mutableAllLocalMapel : MutableLiveData<List<Mapel>>
    private lateinit var mutableAddMapel : MutableLiveData<Response<GlobalWrapper<Mapel>>>
    private lateinit var mutableMapelById : MutableLiveData<Response<GlobalWrapper<Mapel>>>
    private lateinit var mutableDeleteMapelById : MutableLiveData<Response<Unit>>
    private lateinit var mutableUpdateMapelById : MutableLiveData<Response<GlobalWrapper<Mapel>>>

    fun getAllMapel(token : String): LiveData<Response<GlobalWrapper<ArrayList<Mapel>>>>{
        mutableAllMapel = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAllMapel(token)
            mutableAllMapel.postValue(response)
        }
        return mutableAllMapel
    }

    fun addMapel(token: String, mapel: MapelAdd): LiveData<Response<GlobalWrapper<Mapel>>>{
        mutableAddMapel = MutableLiveData()
        GlobalScope.launch {
            val response = repository.addMapel(token, mapel)
            mutableAddMapel.postValue(response)
        }
        return mutableAddMapel
    }

    fun getMapelById(token: String, id:String): LiveData<Response<GlobalWrapper<Mapel>>>{
        mutableMapelById = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getMapelById(token, id)
            mutableMapelById.postValue(response)
        }
        return mutableMapelById
    }

    fun deleteMapelById(token: String, id:String): LiveData<Response<Unit>>{
        mutableDeleteMapelById = MutableLiveData()
        GlobalScope.launch {
            val response = repository.deleteMapelById(token, id)
            mutableDeleteMapelById.postValue(response)
        }
        return mutableDeleteMapelById
    }

    fun updateMapelById(token: String, id:String, mapel: MapelAdd): LiveData<Response<GlobalWrapper<Mapel>>>{
        mutableUpdateMapelById = MutableLiveData()
        GlobalScope.launch {
            val response = repository.updateMapelById(token, id, mapel)
            mutableUpdateMapelById.postValue(response)
        }
        return mutableUpdateMapelById
    }

    fun insertLocalMapel(mapel: Mapel){
        GlobalScope.launch {
            repository.insertLocalMapel(mapel)
        }
    }

    fun insertAllLocalMapel(list : ArrayList<Mapel>){
        GlobalScope.launch {
            repository.insertAllLocalMapel(list)
        }
    }

    fun deleteLocalMapel(mapel: Mapel){
        GlobalScope.launch {
            repository.deleteLocalMapel(mapel)
        }
    }
    fun clearTableMapel(){
        GlobalScope.launch {
            repository.clearTableMapel()
        }
    }

    fun getAllLocalMapel():MutableLiveData<List<Mapel>>{
        mutableAllLocalMapel = MutableLiveData()
        GlobalScope.launch {
            mutableAllLocalMapel.postValue(repository.getAllLocalMapel())
        }
        return mutableAllLocalMapel
    }
}