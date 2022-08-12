package id.deval.raport.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.deval.raport.db.Repository
import id.deval.raport.db.models.Attendance
import id.deval.raport.db.models.request.AttendanceAdd
import id.deval.raport.utils.wrappers.GlobalWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AbsenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private lateinit var mutableAddAttendance : MutableLiveData<GlobalWrapper<Attendance>>

    fun addAttendance(token: String, attendance: AttendanceAdd): LiveData<GlobalWrapper<Attendance>> {
        mutableAddAttendance = MutableLiveData()
        GlobalScope.launch {
            val response = repository.addAttendance(token, attendance)
            mutableAddAttendance.postValue(response)
        }
        return mutableAddAttendance
    }
}