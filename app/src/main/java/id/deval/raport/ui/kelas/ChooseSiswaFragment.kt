package id.deval.raport.ui.kelas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentChooseSiswaBinding
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.RvChooseAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.DummyData

class ChooseSiswaFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentChooseSiswaBinding
    private lateinit var dataSiswa : ArrayList<Siswa>
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseSiswaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataSiswa = DummyData().setDummyDataSiswa()
        with(binding){
            ivChoosesiswaBack.setOnClickListener {
                mainNavController.popBackStack()
            }
            rvChoosesiswaContainer.apply {
                val adapter = RvChooseAdapter<Siswa>("siswa")
                adapter.setData(dataSiswa)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
            }
            mbChoosesiswaSimpan.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }

}