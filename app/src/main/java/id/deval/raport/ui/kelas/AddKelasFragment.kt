package id.deval.raport.ui.kelas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
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
    private var isValid = false
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddKelasBinding.inflate(inflater, container, false)
//        requireActivity().onBackPressedDispatcher.addCallback {
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    mapelViewModel.clearTableMapel()
//                    siswaViewModel.clearTableSiswa()
//                    dataMapel.clear()
//                    dataSiswa.clear()
//                    with(binding){
//                        ivAddkelasBack.isPressed = true
//                    }
//                }
//            }
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataMapel = arrayListOf()
        dataSiswa = arrayListOf()
        val listGuru = mutableListOf<String>()
        val listAccountGuru = mutableListOf<Account>()
        val id = arguments?.getString(Constanta.ID, "")

        with(binding) {
            if (!id.isNullOrEmpty()) {
                kelasViewModel.getClassById(session.token.toString(), id)
                    .observe(viewLifecycleOwner) {
                        tietAddkelasNama.setText(it.data[0].name)
                        tietAddkelasSemester.setText(it.data[0].semester.toString())
                        tietAddkelasTahunajaran.setText(it.data[0].tahunAjaran)
                        val guru = it.data[0].detailGuru?.get(0)
                        if (guru != null) {
                            val macGuru = "${guru.name}/${guru.username}"
                            tietAddkelasGuru.setText(macGuru, false)
                        }
                        dataMapel.clear()
                        it.data[0].mapelDetail?.forEach {
                            if (it != null) {
                                dataMapel.add(it)
                            }
                        }
                        dataSiswa.clear()
                        it.data[0].siswaDetail?.forEach {
                            if (it != null) {
                                dataSiswa.add(it)
                            }
                        }
                        siswaViewModel.insertAllLocalSiswa(dataSiswa)
                        mapelViewModel.insertAllLocalMapel(dataMapel)

                        Log.d("TAG", "onViewCreated: $dataSiswa")
                        Log.d("TAG", "onViewCreated: $dataMapel")
                        includeRvSiswa.rvRvlayoutContainer.apply {
                            val adapter =
                                RvAdapter<Siswa>("siswa", OperationsTypeRv.READ, mainNavController)
                            adapter.setData(dataSiswa)
                            adapter.notifyDataSetChanged()
                            this.adapter = adapter
                            layoutManager =
                                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        }
                        includeRvMapel.rvRvlayoutContainer.apply {
                            val adapter =
                                RvAdapter<Mapel>("mapel", OperationsTypeRv.READ, mainNavController)
                            adapter.setData(dataMapel)
                            adapter.notifyDataSetChanged()
                            this.adapter = adapter
                            layoutManager =
                                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        }
                    }
            } else {
                refreshRvSiswa()
                refreshRvMapel()
            }

            accountViewModel.getAllTeacher(session.token.toString()).observe(viewLifecycleOwner) {
                it.data.forEach {
                    listGuru.add("${it.name}/${it.username}")
                }
                listAccountGuru.addAll(it.data)
                val guruAdapter = ArrayAdapter(requireActivity(), R.layout.list_item, listGuru)
                tietAddkelasGuru.setAdapter(guruAdapter)
            }

            ivAddkelasBack.setOnClickListener {
                mapelViewModel.clearTableMapel()
                siswaViewModel.clearTableSiswa()
                dataMapel.clear()
                dataSiswa.clear()
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
                val nameKelas = tietAddkelasNama.text.toString()
                val semester = tietAddkelasSemester.text.toString()
                val tahunAjaran = tietAddkelasTahunajaran.text.toString()
                var guru = tietAddkelasGuru.text.toString().split("/")[1]
                guru = listAccountGuru.find { it.username == guru }!!.id.toString()

                if (nameKelas.isEmpty()) {
                    tietAddkelasNama.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }
                if (semester.isEmpty()) {
                    tietAddkelasSemester.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }
                if (tahunAjaran.isEmpty()) {
                    tietAddkelasTahunajaran.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }
                if (guru.isEmpty()) {
                    tietAddkelasGuru.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (nameKelas.isNotEmpty() && semester.isNotEmpty() && tahunAjaran.isNotEmpty() && guru.isNotEmpty()) {
                    isValid = true
                }

                val siswaId = arrayListOf<String>()
                val mapelId = arrayListOf<String>()

                dataSiswa.forEach {
                    siswaId.add(it.id)
                }
                dataMapel.forEach {
                    mapelId.add(it.id)
                }

                if (isValid) {
                    val kelas = Kelas(
                        null,
                        siswaId,
                        nameKelas, mapelId, semester.toInt(), tahunAjaran, guru
                    )

                    kelasViewModel.addClass(session.token.toString(), kelas)
                        .observe(viewLifecycleOwner) {
                            mapelViewModel.clearTableMapel()
                            siswaViewModel.clearTableSiswa()
                            dataMapel.clear()
                            dataSiswa.clear()
                            mainNavController.popBackStack()
                            requireContext().showToast("${it.status} menambahkan data kelas")
                        }

                }
            }
        }
    }

    fun refreshRvSiswa() {
        with(binding) {
            siswaViewModel.getAllLocalSiswa().observe(viewLifecycleOwner) {
                dataSiswa.addAll(it)
                includeRvSiswa.rvRvlayoutContainer.apply {
                    val adapter =
                        RvAdapter<Siswa>("siswa", OperationsTypeRv.READ, mainNavController)
                    adapter.setData(dataSiswa)
                    adapter.notifyDataSetChanged()
                    this.adapter = adapter
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

    fun refreshRvMapel() {
        with(binding) {
            mapelViewModel.getAllLocalMapel().observe(viewLifecycleOwner) {
                dataMapel.addAll(it)
                includeRvMapel.rvRvlayoutContainer.apply {
                    val adapter =
                        RvAdapter<Mapel>("mapel", OperationsTypeRv.READ, mainNavController)
                    adapter.setData(dataMapel)
                    adapter.notifyDataSetChanged()
                    this.adapter = adapter
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

}