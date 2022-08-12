package id.deval.raport.ui.absen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private lateinit var _binding : FragmentListAbsenBinding
    private val binding get() = _binding
    private var classId :String = ""
    private var mapelId:String =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListAbsenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataTanggal = DummyData().setDummyAttendance()
        classId = arguments?.getString(Constanta.CLASS_ID) ?: ""
        mapelId = arguments?.getString(Constanta.MAPEL_ID) ?: ""
        with(binding){
            mtvListabsenAbsen.text = dataTanggal.size.toString()
            rvListabsenDate.apply {
                val adapter = RvAbsensiAdapter(dataTanggal, mainNavController, null)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            mbListabsenAdd.setOnClickListener {
                val formatter = SimpleDateFormat("dd/M/yyyy")
                val date = formatter.format(Date())
                val attendance = AttendanceAdd(
                    classId, mapelId, date, null
                )
                absenViewModel.addAttendance(session.token.toString(), attendance).observe(viewLifecycleOwner){
                    Log.d("TAG", "onViewCreated: ${it.data}")
                    mainNavController.navigate(R.id.action_listAbsenFragment_to_absenDetailFragment)
                }
            }
        }
    }
}