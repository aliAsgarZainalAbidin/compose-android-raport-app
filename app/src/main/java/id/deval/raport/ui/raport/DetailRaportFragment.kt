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
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.ui.adapter.RvTugasAdapter
import id.deval.raport.utils.*

class DetailRaportFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentDetailRaportBinding
    private val binding get() = _binding

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
        with(binding) {
            Log.d("TAG", "onViewCreated: $role")
            includeRvSiswa.mtvRvlayoutTitle.text = "Tugas"
            includeRvSiswa.mtvRvlayoutTitle.setTextColor(resources.getColor(R.color.white))

            siswaViewModel.getSiswaById(session.token.toString(), siswaId.toString())
                .observe(viewLifecycleOwner) {
                    includeDetailraportContainer.mtvRvitemName.text = it.data.name
                    includeDetailraportContainer.mtvRvitemNis.text = it.data.nis
                }

            raportViewModel.getSpesifikRaport(
                session.token.toString(),
                classId.toString(),
                mapelId.toString(),
                siswaId.toString()
            ).observe(viewLifecycleOwner) {
                val raport = it.data
                var nilaiSikap= "0"
                var nilaiUts= "0"
                var nilaiUas= "0"
                if (raport.nilaiSikap.toString() != "null") {
                    nilaiSikap = raport.nilaiSikap.toString()
                }
                if (raport.nilaiUTS.toString() != "null") {
                    nilaiUts = raport.nilaiSikap.toString()
                }
                if (raport.nilaiUAS.toString() != "null") {
                    nilaiUas = raport.nilaiSikap.toString()
                }
                tietDetailraportSikap.setText(nilaiSikap)
                tilDetailraportKeterampilan.isEnabled = false
                tietDetailraportUts.setText(nilaiUts)
                tietDetailraportUas.setText(nilaiUas)
                tietDetailraportDesc.setText(raport.deskripsi)

                val listTugas = arrayListOf<Tugas>()
                it.data.tugasDetail?.forEach { tugas ->
                    if (tugas!=null){
                        listTugas.add(tugas)
                    }
                }

                Log.d(TAG, "onViewCreated: ${it.data.id}")

                when (role) {
                    "guru" -> viewAsGuru(listTugas, it.data.id.toString())
                    "orangtua" -> viewAsOrangTua(listTugas)
                }

//                tietDetailraportKeterampilan.setText()
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