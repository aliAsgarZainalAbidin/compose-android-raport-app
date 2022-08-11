package id.deval.raport.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.StringSiswa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StringSiswaViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){
    private lateinit var mutableAllSiswa : MutableLiveData<List<StringSiswa>>

    fun insertSiswa(siswa: StringSiswa){
        GlobalScope.launch {
            repository.insertSiswa(siswa)
        }
    }

    fun insertAllSiswa(list : ArrayList<StringSiswa>){
        GlobalScope.launch {
            repository.insertAllSiswa(list)
        }
    }

    fun deleteSiswa(siswa: StringSiswa){
        GlobalScope.launch {
            repository.deleteSiswa(siswa)
        }
    }

    fun getAllSiswa():MutableLiveData<List<StringSiswa>>{
        mutableAllSiswa = MutableLiveData()
        GlobalScope.launch {
            mutableAllSiswa.postValue(repository.getAllSiswa())
        }
        return mutableAllSiswa
    }
}