package id.deval.raport.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Pesan
import id.deval.raport.db.models.request.GrowthAdd
import id.deval.raport.db.models.request.NoteAdd
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PesanViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private lateinit var mutableGetPesanById : MutableLiveData<Response<GlobalWrapper<Pesan>>>
    private lateinit var mutableAddGrowth : MutableLiveData<Response<GlobalWrapper<Pesan>>>
    private lateinit var mutableAddNote : MutableLiveData<Response<GlobalWrapper<Pesan>>>

    fun getPesanById(token:String, id:String): LiveData<Response<GlobalWrapper<Pesan>>>{
        mutableGetPesanById = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getPesanById(token, id)
            mutableGetPesanById.postValue(response)
        }
        return mutableGetPesanById
    }
    fun addNote(token:String, noteAdd: NoteAdd): LiveData<Response<GlobalWrapper<Pesan>>>{
        mutableAddNote = MutableLiveData()
        GlobalScope.launch {
            val response = repository.addNote(token, noteAdd)
            mutableAddNote.postValue(response)
        }
        return mutableAddNote
    }
    fun addGrowth(token:String, growthAdd: GrowthAdd): LiveData<Response<GlobalWrapper<Pesan>>>{
        mutableAddGrowth = MutableLiveData()
        GlobalScope.launch {
            val response = repository.addGrowth(token, growthAdd)
            mutableAddGrowth.postValue(response)
        }
        return mutableAddGrowth
    }
}