package id.deval.raport.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Mapel
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapelViewModel @Inject constructor(
  private val repository: Repository
) : ViewModel() {
    private lateinit var mutableAllMapel : MutableLiveData<GlobalWrapper<ArrayList<Mapel>>>

    fun getAllMapel(token : String): LiveData<GlobalWrapper<ArrayList<Mapel>>>{
        mutableAllMapel = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAllMapel(token)
            mutableAllMapel.postValue(response)
        }
        return mutableAllMapel
    }
}