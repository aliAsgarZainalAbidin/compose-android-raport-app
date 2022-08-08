package id.deval.raport.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Siswa
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SiswaViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private lateinit var mutablelistSiswa : MutableLiveData<GlobalWrapper<ArrayList<Siswa>>>

    fun getAllSiswa(token : String) : LiveData<GlobalWrapper<ArrayList<Siswa>>>{
        mutablelistSiswa = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAllSiswa(token)
            mutablelistSiswa.postValue(response)
        }
        return mutablelistSiswa
    }

}