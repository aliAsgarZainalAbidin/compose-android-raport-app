package id.deval.raport.ui.absen

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import id.deval.raport.R
import id.deval.raport.databinding.FragmentListAbsenBinding
import id.deval.raport.db.models.Attendance
import id.deval.raport.db.models.request.AttendanceAdd
import id.deval.raport.ui.adapter.RvAbsensiAdapter
import id.deval.raport.utils.*
import id.deval.raport.utils.wrappers.GlobalWrapper
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ListAbsenFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentListAbsenBinding
    private val binding get() = _binding
    private var classId: String = ""
    private var mapelId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListAbsenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        classId = arguments?.getString(Constanta.CLASS_ID) ?: ""
        mapelId = arguments?.getString(Constanta.MAPEL_ID) ?: ""
        val mapelName = arguments?.getString(Constanta.MAPEL_NAME)
        with(binding) {
            ivListabsenBack.setOnClickListener {
                try {
                    mainNavController.popBackStack()
                }catch (e: Exception){
                    Log.d(TAG, "onViewCreated: $e")
                }
            }

            mtvListabsenName.text = mapelName
            absenViewModel.getAttendance(session.token.toString(), classId, mapelId)
                .observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        mtvListabsenAbsen.text = it.body()?.data?.size.toString()

                        rvListabsenDate.apply {
                            val adapter =
                                RvAbsensiAdapter(it.body()?.data!!, mainNavController, null)
                            adapter.notifyDataSetChanged()
                            this.adapter = adapter
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        }
                    } else {
                        requireActivity().showToast(it.message())
                    }
                }

            mbListabsenAdd.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DATE)
                var date: String

                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { p0, p1, p2, p3 ->
                        val month = p2.plus(1).parseIntToMonth()
                        date = "$p3 $month $p1"
                        val attendance = AttendanceAdd(
                            classId, mapelId, date, null
                        )
                        absenViewModel.addAttendance(session.token.toString(), attendance)
                            .observe(viewLifecycleOwner) {
                                if (it.isSuccessful) {
                                    try {
                                        val bundle = bundleOf()
                                        bundle.putString(Constanta.DATE, date)
                                        bundle.putString(Constanta.ID, it.body()?.data!!.id)
                                        mainNavController.navigate(
                                            R.id.action_listAbsenFragment_to_absenDetailFragment,
                                            bundle
                                        )
                                    }catch (e: Exception){
                                        Log.d(TAG, "onViewCreated: $e")
                                    }
                                } else {
                                    requireContext().showToast(it.message())
                                }
                            }
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }
        }
    }
}