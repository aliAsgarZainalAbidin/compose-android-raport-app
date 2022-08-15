package id.deval.raport.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Absen
import id.deval.raport.db.models.Attendance
import id.deval.raport.db.models.Siswa
import id.deval.raport.db.models.request.AttendanceAdd
import id.deval.raport.db.response.ResponseAttendance
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AbsenViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private lateinit var mutableAddAttendance: MutableLiveData<Response<GlobalWrapper<Attendance>>>
    private lateinit var mutableGetLocalAbsen: MutableLiveData<List<Absen>>
    private lateinit var mutableAttendanceById: MutableLiveData<Response<GlobalWrapper<ArrayList<ResponseAttendance>>>>
    private lateinit var mutableUpdateAttendanceById: MutableLiveData<Response<GlobalWrapper<ResponseAttendance>>>
    private lateinit var mutableGetAttendance: MutableLiveData<Response<GlobalWrapper<ArrayList<Attendance>>>>
    private lateinit var mutableGetAttendanceBySiswaId: MutableLiveData<Response<GlobalWrapper<ArrayList<Attendance>>>>

    fun addAttendance(
        token: String,
        attendance: AttendanceAdd
    ): LiveData<Response<GlobalWrapper<Attendance>>> {
        mutableAddAttendance = MutableLiveData()
        GlobalScope.launch {
            val response = repository.addAttendance(token, attendance)
            mutableAddAttendance.postValue(response)
        }
        return mutableAddAttendance
    }

    fun getAttendanceById(
        token: String,
        id: String
    ): LiveData<Response<GlobalWrapper<ArrayList<ResponseAttendance>>>> {
        mutableAttendanceById = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAttendanceById(token, id)
            mutableAttendanceById.postValue(response)
        }
        return mutableAttendanceById
    }

    fun updateAttendanceById(
        token: String,
        id: String,
        attendance: AttendanceAdd
    ): LiveData<Response<GlobalWrapper<ResponseAttendance>>> {
        mutableUpdateAttendanceById = MutableLiveData()
        GlobalScope.launch {
            val response = repository.updateAttendanceById(token, id, attendance)
            mutableUpdateAttendanceById.postValue(response)
        }
        return mutableUpdateAttendanceById
    }

    fun getAttendance(
        token: String,
        classId: String,
        mapelId: String
    ): LiveData<Response<GlobalWrapper<ArrayList<Attendance>>>> {
        mutableGetAttendance = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAttendance(token, classId, mapelId)
            mutableGetAttendance.postValue(response)
        }
        return mutableGetAttendance
    }

    fun getAttendanceBySiswaId(
        token: String,
        siswaId: String,
        mapelId: String
    ): LiveData<Response<GlobalWrapper<ArrayList<Attendance>>>> {
        mutableGetAttendanceBySiswaId = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAttendanceBySiswaId(token, siswaId, mapelId)
            mutableGetAttendanceBySiswaId.postValue(response)
        }
        return mutableGetAttendanceBySiswaId
    }

    fun insertLocalAbsen(absen: Absen) {
        GlobalScope.launch {
            repository.insertAbsen(absen)
        }
    }

    fun updateLocalAbsen(absen: Absen) {
        GlobalScope.launch {
            repository.updateAbsen(absen)
        }
    }

    fun insertAllLocalAbsen(list: ArrayList<Absen>) {
        GlobalScope.launch {
            repository.insertAllAbsen(list)
        }
    }

    fun deleteLocalAbsen(absen: Absen) {
        GlobalScope.launch {
            repository.deleteAbsen(absen)
        }
    }

    fun clearTableAbsen() {
        GlobalScope.launch {
            repository.clearTableAbsen()
        }
    }

    fun getAllLocalAbsen(): MutableLiveData<List<Absen>> {
        mutableGetLocalAbsen = MutableLiveData()
        GlobalScope.launch {
            mutableGetLocalAbsen.postValue(repository.getAllAbsen())
        }
        return mutableGetLocalAbsen
    }
}