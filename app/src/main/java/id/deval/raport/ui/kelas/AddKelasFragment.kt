package id.deval.raport.ui.kelas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddKelasBinding
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.RvAdapter
import id.deval.raport.utils.*

class AddKelasFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAddKelasBinding
    private lateinit var dataMapel: ArrayList<Mapel>
    private lateinit var dataSiswa: ArrayList<Siswa>
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddKelasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataMapel = DummyData().setDummyDataMapel()
        dataSiswa = DummyData().setDummyDataSiswa()
        with(binding) {

            ivAddkelasBack.setOnClickListener {
                mainNavController.popBackStack()
            }
            includeRvSiswa.mtvRvlayoutTitle.setTextColor(resources.getColor(R.color.white))
            includeRvSiswa.mtvRvlayoutTitle.text = "Siswa"
            includeRvSiswa.mtvRvlayoutAdd.show()
            includeRvSiswa.mtvRvlayoutAdd.setOnClickListener {
                mainNavController.navigate(R.id.action_addKelasFragment_to_chooseSiswaFragment)
            }
            includeRvSiswa.mtvRvlayoutAdd.text = "Tambah Siswa"
            includeRvSiswa.mtvRvlayoutViewmore.hide()
            includeRvSiswa.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Siswa>("siswa", OperationsTypeRv.READ)
                adapter.setData(dataSiswa)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            includeRvMapel.mtvRvlayoutTitle.setTextColor(resources.getColor(R.color.white))
            includeRvMapel.mtvRvlayoutTitle.text = "Mapel"
            includeRvMapel.mtvRvlayoutAdd.show()
            includeRvMapel.mtvRvlayoutAdd.setOnClickListener {
                mainNavController.navigate(R.id.action_addKelasFragment_to_chooseMapelFragment)
            }
            includeRvMapel.mtvRvlayoutAdd.text = "Tambah Mapel"
            includeRvMapel.mtvRvlayoutViewmore.hide()
            includeRvMapel.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Mapel>("mapel", OperationsTypeRv.READ)
                adapter.setData(dataMapel)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            mbAddkelasSimpan.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }

}