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
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class SiswaViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private lateinit var mutablelistSiswa: MutableLiveData<GlobalWrapper<ArrayList<Siswa>>>
    private lateinit var mutableAddSiswa: MutableLiveData<GlobalWrapper<Siswa>>

    fun getAllSiswa(token: String): LiveData<GlobalWrapper<ArrayList<Siswa>>> {
        mutablelistSiswa = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAllSiswa(token)
            mutablelistSiswa.postValue(response)
        }
        return mutablelistSiswa
    }

    fun addSiswa(
        token: String, name: MultipartBody.Part,
        nis: MultipartBody.Part,
        tempatLahir: MultipartBody.Part,
        tanggalLahir: MultipartBody.Part,
        address: MultipartBody.Part,
        education: MultipartBody.Part,
        religion: MultipartBody.Part,
        gender: MultipartBody.Part,
        namaAyah: MultipartBody.Part,
        namaIbu: MultipartBody.Part,
        pekerjaanAyah: MultipartBody.Part,
        pekerjaanIbu: MultipartBody.Part,
        namaWali: MultipartBody.Part,
        pekerjaanWali: MultipartBody.Part,
        alamatWali: MultipartBody.Part,
        phone: MultipartBody.Part,
        photo: MultipartBody.Part
    ): LiveData<GlobalWrapper<Siswa>> {
        mutableAddSiswa = MutableLiveData()
        GlobalScope.launch {
            val response = repository.addSiswa(
                token,
                name,
                nis,
                tempatLahir,
                tanggalLahir,
                address,
                education,
                religion,
                gender,
                namaAyah,
                namaIbu,
                pekerjaanAyah,
                pekerjaanIbu,
                namaWali,
                pekerjaanWali,
                alamatWali,
                phone,
                photo
            )
            mutableAddSiswa.postValue(response)
        }
        return mutableAddSiswa
    }

}