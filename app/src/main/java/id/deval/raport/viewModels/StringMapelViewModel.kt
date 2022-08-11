package id.deval.raport.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.StringMapel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StringMapelViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){
    private lateinit var mutableAllMapel : MutableLiveData<List<StringMapel>>

    fun insertMapel(mapel: StringMapel){
        GlobalScope.launch {
            repository.insertMapel(mapel)
        }
    }

    fun insertAllMapel(list : ArrayList<StringMapel>){
        GlobalScope.launch {
            repository.insertAllMapel(list)
        }
    }

    fun deleteMapel(mapel: StringMapel){
        GlobalScope.launch {
            repository.deleteMapel(mapel)
        }
    }

    fun getAllMapel():MutableLiveData<List<StringMapel>>{
        mutableAllMapel = MutableLiveData()
        GlobalScope.launch {
            mutableAllMapel.postValue(repository.getAllMapel())
        }
        return mutableAllMapel
    }
}