package id.deval.raport.ui.raport

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentDetailRaportBinding
import id.deval.raport.db.models.Tugas
import id.deval.raport.db.models.request.RaportAdd
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.ui.adapter.RvTugasAdapter
import id.deval.raport.utils.*

class DetailRaportFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentDetailRaportBinding
    private val binding get() = _binding
    private lateinit var listTugasId : ArrayList<String>
    private var idRaport = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailRaportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val role = arguments?.getString(Constanta.ROLE).toString()
        val classId = arguments?.getString(Constanta.CLASS_ID)
        val mapelId = arguments?.getString(Constanta.MAPEL_ID)
        val siswaId = arguments?.getString(Constanta.SISWA_ID)
        listTugasId = arrayListOf()
        with(binding) {
            mtvDetailraportName.text = "Raport"
            ivDetailraportBack.setOnClickListener {
                mainNavController.popBackStack()
            }
            includeRvSiswa.mtvRvlayoutTitle.text = "Tugas"
            includeRvSiswa.mtvRvlayoutAdd.text = "Tambah Tugas"
            includeRvSiswa.mtvRvlayoutTitle.setTextColor(resources.getColor(R.color.white))

            siswaViewModel.getSiswaById(session.token.toString(), siswaId.toString())
                .observe(viewLifecycleOwner) {
                    includeDetailraportContainer.mtvRvitemName.text = it.body()?.data!!.name
                    includeDetailraportContainer.mtvRvitemNis.text = it.body()?.data!!.nis
                }

            Log.d(TAG, "onViewCreated: $classId, $mapelId, $siswaId")
            raportViewModel.getSpesifikRaport(
                session.token.toString(),
                classId.toString(),
                mapelId.toString(),
                siswaId.toString()
            ).observe(viewLifecycleOwner) {
                if (it.isSuccessful){
                    val raport = it.body()?.data!!
                    var nilaiSikap= "0"
                    var nilaiUts= "0"
                    var nilaiUas= "0"

                    if (raport.nilaiSikap.toString() != "null") {
                        nilaiSikap = raport.nilaiSikap.toString()
                    }
                    if (raport.nilaiUTS.toString() != "null") {
                        nilaiUts = raport.nilaiUTS.toString()
                    }
                    if (raport.nilaiUAS.toString() != "null") {
                        nilaiUas = raport.nilaiUAS.toString()
                    }
                    tietDetailraportSikap.setText(nilaiSikap)
                    tilDetailraportKeterampilan.isEnabled = false
                    tietDetailraportUts.setText(nilaiUts)
                    tietDetailraportUas.setText(nilaiUas)
                    tietDetailraportDesc.setText(raport.deskripsi)

                    val listTugas = arrayListOf<Tugas>()
                    var nilaiKeterampilan = 0
                    it.body()?.data!!.tugasDetail?.forEach { tugas ->
                        if (tugas!=null){
                            listTugasId.add(tugas.id)
                            listTugas.add(tugas)
                            nilaiKeterampilan += tugas.nilai!!
                        }
                    }
                    if (listTugas.size>0){
                        nilaiKeterampilan = nilaiKeterampilan.div(listTugas.size)
                    }
                    tietDetailraportKeterampilan.setText(nilaiKeterampilan.toString())

                    idRaport = it.body()?.data!!.id.toString()
                    when (role) {
                        "guru" -> viewAsGuru(listTugas, idRaport)
                        "orangtua" -> viewAsOrangTua(listTugas)
                    }
                } else {
                    requireContext().showToast(it.message())
                }
            }

            mbDetailraportSimpan.setOnClickListener {
                val nilaiSikap = tietDetailraportSikap.text.toString()
                val uts = tietDetailraportUts.text.toString()
                val uas = tietDetailraportUas.text.toString()
                val desc = tietDetailraportDesc.text.toString()


                val raport = RaportAdd(
                    listTugasId, nilaiSikap.toInt(), uts.toInt(), desc, uas.toInt(), classId, siswaId, mapelId
                )
                raportViewModel.updateRaport(session.token.toString(), raport).observe(viewLifecycleOwner){
                    if (it.isSuccessful){
                        requireContext().showToast("${it.body()?.status} men-update data raport")
                    } else {
                        requireContext().showToast(it.message())
                    }
                }
            }
        }
    }

    fun viewAsGuru(listTugas: ArrayList<Tugas>, raportId:String) {
        with(binding) {
            includeRvSiswa.mtvRvlayoutViewmore.invisible()
            includeRvSiswa.mtvRvlayoutAdd.show()
            includeRvSiswa.mtvRvlayoutAdd.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString(Constanta.ID,raportId)
                mainNavController.navigate(R.id.action_detailRaportFragment_to_addTugasFragment, bundle)
            }

            includeRvSiswa.rvRvlayoutContainer.apply {
                val adapter = RvTugasAdapter(listTugas, mainNavController)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    fun viewAsOrangTua(listTugas: ArrayList<Tugas>) {
        with(binding) {
            includeRvSiswa.mtvRvlayoutViewmore.invisible()
            includeRvSiswa.mtvRvlayoutAdd.invisible()
            mbDetailraportSimpan.hide()

            tilDetailraportSikap.isEnabled = false
            tilDetailraportKeterampilan.isEnabled = false
            tilDetailraportUts.isEnabled = false
            tilDetailraportUas.isEnabled = false
            tilDetailraportDesc.isEnabled = false

            includeRvSiswa.rvRvlayoutContainer.apply {
                val adapter = RvTugasAdapter(listTugas, null)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

}