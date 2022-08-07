package id.deval.raport.ui.absen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAbsenOrangtuaBinding
import id.deval.raport.ui.adapter.RvAbsensiAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.DummyData

class AbsenOrangtuaFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentAbsenOrangtuaBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAbsenOrangtuaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataAttendance = DummyData().setDummyAttendance()
        with(binding){
            mtvAbsenorangtuaName.text = "Ali Asgar"
            mtvAbsenorangtuaNis.text = "60200117039"

            rvAbsenorangtuaDate.apply {
                val adapter = RvAbsensiAdapter(dataAttendance, mainNavController, "siswa1")
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

}