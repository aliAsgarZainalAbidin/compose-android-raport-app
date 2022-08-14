package id.deval.raport.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Absen
import id.deval.raport.db.models.Attendance
import id.deval.raport.db.models.Siswa
import id.deval.raport.db.models.request.AttendanceAdd
import id.deval.raport.db.response.ResponseAttendance
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AbsenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private lateinit var mutableAddAttendance : MutableLiveData<GlobalWrapper<Attendance>>
    private lateinit var mutableGetLocalAbsen : MutableLiveData<List<Absen>>
    private lateinit var mutableAttendanceById : MutableLiveData<GlobalWrapper<ArrayList<ResponseAttendance>>>
    private lateinit var mutableUpdateAttendanceById : MutableLiveData<GlobalWrapper<ResponseAttendance>>
    private lateinit var mutableGetAttendance : MutableLiveData<GlobalWrapper<ArrayList<Attendance>>>
    private lateinit var mutableGetAttendanceBySiswaId : MutableLiveData<GlobalWrapper<ArrayList<Attendance>>>

    fun addAttendance(token: String, attendance: AttendanceAdd): LiveData<GlobalWrapper<Attendance>> {
        mutableAddAttendance = MutableLiveData()
        GlobalScope.launch {
            val response = repository.addAttendance(token, attendance)
            mutableAddAttendance.postValue(response)
        }
        return mutableAddAttendance
    }

    fun getAttendanceById(token: String, id: String): LiveData<GlobalWrapper<ArrayList<ResponseAttendance>>> {
        mutableAttendanceById = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAttendanceById(token, id)
            mutableAttendanceById.postValue(response)
        }
        return mutableAttendanceById
    }

    fun updateAttendanceById(token: String, id: String, attendance: AttendanceAdd): LiveData<GlobalWrapper<ResponseAttendance>> {
        mutableUpdateAttendanceById = MutableLiveData()
        GlobalScope.launch {
            val response = repository.updateAttendanceById(token, id, attendance)
            mutableUpdateAttendanceById.postValue(response)
        }
        return mutableUpdateAttendanceById
    }

    fun getAttendance(token: String, classId:String, mapelId:String): LiveData<GlobalWrapper<ArrayList<Attendance>>> {
        mutableGetAttendance = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAttendance(token, classId, mapelId)
            mutableGetAttendance.postValue(response)
        }
        return mutableGetAttendance
    }

    fun getAttendanceBySiswaId(token: String, siswaId:String, mapelId:String): LiveData<GlobalWrapper<ArrayList<Attendance>>> {
        mutableGetAttendanceBySiswaId = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAttendanceBySiswaId(token, siswaId, mapelId)
            mutableGetAttendanceBySiswaId.postValue(response)
        }
        return mutableGetAttendanceBySiswaId
    }

    fun insertLocalAbsen(absen: Absen){
        GlobalScope.launch {
            repository.insertAbsen(absen)
        }
    }

    fun updateLocalAbsen(absen: Absen){
        GlobalScope.launch {
            repository.updateAbsen(absen)
        }
    }

    fun insertAllLocalAbsen(list : ArrayList<Absen>){
        GlobalScope.launch {
            repository.insertAllAbsen(list)
        }
    }

    fun deleteLocalAbsen(absen: Absen){
        GlobalScope.launch {
            repository.deleteAbsen(absen)
        }
    }
    fun clearTableAbsen(){
        GlobalScope.launch {
            repository.clearTableAbsen()
        }
    }

    fun getAllLocalAbsen():MutableLiveData<List<Absen>>{
        mutableGetLocalAbsen = MutableLiveData()
        GlobalScope.launch {
            mutableGetLocalAbsen.postValue(repository.getAllAbsen())
        }
        return mutableGetLocalAbsen
    }
}