package id.deval.raport.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Attendance
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
    private lateinit var mutableAttendanceById : MutableLiveData<GlobalWrapper<ArrayList<ResponseAttendance>>>
    private lateinit var mutableGetAttendance : MutableLiveData<GlobalWrapper<ArrayList<Attendance>>>

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

    fun getAttendance(token: String, classId:String, mapelId:String): LiveData<GlobalWrapper<ArrayList<Attendance>>> {
        mutableGetAttendance = MutableLiveData()
        GlobalScope.launch {
            val response = repository.getAttendance(token, classId, mapelId)
            mutableGetAttendance.postValue(response)
        }
        return mutableGetAttendance
    }
}