package id.deval.raport.ui.kelas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentChooseMapelBinding
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.RvChooseAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.DummyData

class ChooseMapelFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentChooseMapelBinding
    private val binding get() = _binding
    private lateinit var dataMapel : ArrayList<Mapel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseMapelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataMapel = DummyData().setDummyDataMapel()
        with(binding){
            ivChoosemapelBack.setOnClickListener {
                mainNavController.popBackStack()
            }
            rvChoosemapelContainer.apply {
                val adapter = RvChooseAdapter<Mapel>("mapel")
                adapter.setData(dataMapel)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL, false)
            }
            mbChoosemapelSimpan.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }
}