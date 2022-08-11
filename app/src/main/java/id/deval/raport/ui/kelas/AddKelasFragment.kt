package id.deval.raport.ui.kelas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddKelasBinding
import id.deval.raport.db.models.*
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*

class AddKelasFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAddKelasBinding
    private lateinit var dataMapel: ArrayList<Mapel>
    private lateinit var dataSiswa: ArrayList<Siswa>
    private var dataStringSiswa : ArrayList<StringSiswa> = arrayListOf()
    private var dataStringMapel : ArrayList<StringMapel> = arrayListOf()
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

        dataMapel = arrayListOf()
        dataSiswa = arrayListOf()

        with(binding) {
            siswaViewModel.getAllLocalSiswa().observe(viewLifecycleOwner) {
                dataSiswa.addAll(it)
                includeRvSiswa.rvRvlayoutContainer.apply {
                    val adapter = RvAdapter<Siswa>("siswa", OperationsTypeRv.READ, mainNavController)
                    adapter.setData(dataSiswa)
                    adapter.notifyDataSetChanged()
                    this.adapter = adapter
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }

            mapelViewModel.getAllLocalMapel().observe(viewLifecycleOwner){
                dataMapel.addAll(it)
                includeRvMapel.rvRvlayoutContainer.apply {
                    val adapter = RvAdapter<Mapel>("mapel", OperationsTypeRv.READ, mainNavController)
                    adapter.setData(dataMapel)
                    adapter.notifyDataSetChanged()
                    this.adapter = adapter
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }

            accountViewModel.getAllTeacher(session.token.toString()).observe(viewLifecycleOwner) {
                val listGuru = mutableListOf<String>()
                it.data.forEach {
                    listGuru.add("${it.name}/${it.username}")
                }
                val guruAdapter = ArrayAdapter(requireActivity(), R.layout.list_item, listGuru)
                tietAddkelasGuru.setAdapter(guruAdapter)
            }

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

            includeRvMapel.mtvRvlayoutTitle.setTextColor(resources.getColor(R.color.white))
            includeRvMapel.mtvRvlayoutTitle.text = "Mapel"
            includeRvMapel.mtvRvlayoutAdd.show()
            includeRvMapel.mtvRvlayoutAdd.setOnClickListener {
                mainNavController.navigate(R.id.action_addKelasFragment_to_chooseMapelFragment)
            }
            includeRvMapel.mtvRvlayoutAdd.text = "Tambah Mapel"
            includeRvMapel.mtvRvlayoutViewmore.hide()

            mbAddkelasSimpan.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }

}