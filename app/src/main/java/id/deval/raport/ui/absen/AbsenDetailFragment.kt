package id.deval.raport.ui.absen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAbsenDetailBinding
import id.deval.raport.databinding.RvAbsenSiswaBinding
import id.deval.raport.ui.adapter.RvAbsenSiswaAdapter
import id.deval.raport.ui.adapter.RvAbsensiAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.DummyData

class AbsenDetailFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentAbsenDetailBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAbsenDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataKehadiran = DummyData().setDummyDataAbsenSiswa()
        with(binding){
            mtvDetailabsenAbsen.text = dataKehadiran.size.toString()
            rvDetailabsenDate.apply {
                val adapter = RvAbsenSiswaAdapter(dataKehadiran)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

}