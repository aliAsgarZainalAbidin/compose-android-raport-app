package id.deval.raport.ui.absen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentListAbsenBinding
import id.deval.raport.ui.adapter.RvAbsensiAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.DummyData

class ListAbsenFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentListAbsenBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListAbsenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataTanggal = DummyData().setDummyDataTanggal()
        with(binding){
            mtvListabsenAbsen.text = dataTanggal.size.toString()
            rvListabsenDate.apply {
                val adapter = RvAbsensiAdapter(dataTanggal, mainNavController)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            mbListabsenAdd.setOnClickListener {
                mainNavController.navigate(R.id.action_listAbsenFragment_to_absenDetailFragment)
            }
        }
    }
}