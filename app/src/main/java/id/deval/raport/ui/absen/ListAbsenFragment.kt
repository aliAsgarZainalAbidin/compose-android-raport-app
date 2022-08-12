package id.deval.raport.ui.absen

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentListAbsenBinding
import id.deval.raport.db.models.Attendance
import id.deval.raport.db.models.request.AttendanceAdd
import id.deval.raport.ui.adapter.RvAbsensiAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.DummyData
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
        with(binding) {
            absenViewModel.getAttendance(session.token.toString(), classId, mapelId)
                .observe(viewLifecycleOwner) {
                    mtvListabsenAbsen.text = it.data.size.toString()

                    rvListabsenDate.apply {
                        val adapter = RvAbsensiAdapter(it.data, mainNavController, null)
                        adapter.notifyDataSetChanged()
                        this.adapter = adapter
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    }
                }

            mbListabsenAdd.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                var date: String

                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    object : DatePickerDialog.OnDateSetListener {
                        override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                            date = "$p3-$p2-$p1"
                            val attendance = AttendanceAdd(
                                classId, mapelId, date, null
                            )
                            absenViewModel.addAttendance(session.token.toString(), attendance)
                                .observe(viewLifecycleOwner) {
                                    Log.d("TAG", "onViewCreated: ${it.data}")
                                    val bundle = bundleOf()
                                    bundle.putString(Constanta.DATE, date)
                                    bundle.putString(Constanta.ID, it.data.id)
                                    mainNavController.navigate(
                                        R.id.action_listAbsenFragment_to_absenDetailFragment,
                                        bundle
                                    )
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