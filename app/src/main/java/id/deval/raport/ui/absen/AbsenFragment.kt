package id.deval.raport.ui.absen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAbsenBinding
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.RvAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.DummyData
import id.deval.raport.utils.OperationsTypeRv
import id.deval.raport.utils.invisible

class AbsenFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentAbsenBinding
    private val binding get() = _binding
    private lateinit var dataMapel: ArrayList<Mapel>
    private lateinit var dataSiswa: ArrayList<Siswa>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAbsenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataMapel = DummyData().setDummyDataMapel()
        dataSiswa = DummyData().setDummyDataSiswa()
        with(binding){
            mtvAbsenMapel.text = dataMapel.size.toString()
            mtvAbsenSiswa.text = dataSiswa.size.toString()

            includeRvMapel.mtvRvlayoutTitle.text = "Mata Pelajaran"
            includeRvMapel.mtvRvlayoutAdd.invisible()
            includeRvMapel.mtvRvlayoutViewmore.invisible()
            includeRvMapel.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Mapel>("mapel", OperationsTypeRv.READ, mainNavController)
                adapter.setData(dataMapel)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }
}