package id.deval.raport.ui.raport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentRaportBinding
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*

class RaportFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentRaportBinding
    private val binding get() = _binding
    private lateinit var dataSiswa: ArrayList<Siswa>
    private lateinit var role : String
    private lateinit var dataMapel: ArrayList<Mapel>
    private lateinit var classId: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRaportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataMapel = arrayListOf()
        dataSiswa = DummyData().setDummyDataSiswa()
        role = arguments?.getString(Constanta.ROLE).toString()


        with(binding){
            mtvRaportMapel.text = dataMapel.size.toString()
            mtvRaportSiswa.text = dataSiswa.size.toString()

            includeRvMapel.mtvRvlayoutTitle.text = "Mata Pelajaran"
            includeRvMapel.mtvRvlayoutAdd.invisible()
            includeRvMapel.mtvRvlayoutViewmore.invisible()

            kelasViewModel.getClassByIdGuru(session.token.toString(), session.id.toString()).observe(viewLifecycleOwner){
                mtvRaportMapel.text = it.data.mapelDetail?.size.toString()
                mtvRaportSiswa.text = it.data.siswaId?.size.toString()
                classId = it.data.id.toString()

                it.data.mapelDetail?.forEach { mapel->
                    if (mapel!=null){
                        dataMapel.add(mapel)
                    }
                }
                when(role){
                    "guru" -> viewAsGuru()
                    "orangtua" -> viewAsOrangTua()
                }
            }
        }
    }

    fun viewAsOrangTua(){
        with(binding){
            ivRaportPerson.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString(Constanta.ROLE, role)
                mainNavController.navigate(R.id.action_baseFragment_to_addSiswaFragment, bundle)
            }

            includeRvMapel.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Mapel>("mapel-raport-orangtua", OperationsTypeRv.READ, mainNavController)
                adapter.setData(dataMapel)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    fun viewAsGuru(){
        with(binding){
            includeRvMapel.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Mapel>("mapel-raport", OperationsTypeRv.READ, mainNavController)
                adapter.setData(dataMapel)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

}