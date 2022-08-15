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
import id.deval.raport.db.event.CommonParams
import id.deval.raport.db.event.EventToDetailRaport
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*
import org.greenrobot.eventbus.Subscribe

class RaportFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentRaportBinding
    private val binding get() = _binding
    private lateinit var dataSiswa: ArrayList<Siswa>
    private lateinit var role : String
    private lateinit var dataMapel: ArrayList<Mapel>
    private lateinit var classId: String
    private var siswaId = ""


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
            mtvRaportName.text = session.name

            includeRvMapel.mtvRvlayoutTitle.text = "Mata Pelajaran"
            includeRvMapel.mtvRvlayoutAdd.invisible()
            includeRvMapel.mtvRvlayoutViewmore.invisible()

            when(role){
                "guru" -> viewAsGuru()
                "orangtua" -> viewAsOrangTua()
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

            siswaViewModel.getSiswaByNIS(session.token.toString(), session.username.toString()).observe(viewLifecycleOwner){
                if (it.isSuccessful){
                    siswaId = it.body()?.data!!.id
                } else {
                    requireContext().showToast(it.message())
                }
            }

            kelasViewModel.getClassByNis(session.token.toString(), session.username.toString()).observe(viewLifecycleOwner){
                if (it.isSuccessful){
                    mtvRaportMapel.text = it.body()?.data!!.mapelDetail?.size.toString()
                    mtvRaportSiswa.text = it.body()?.data!!.siswaId?.size.toString()
                    mtvRaportTitletotal.text = "Total Mapel & Siswa ${it.body()?.data!!.name}"
                    classId = it.body()?.data!!.id.toString()

                    it.body()?.data!!.mapelDetail?.forEach { mapel->
                        if (mapel!=null){
                            dataMapel.add(mapel)
                        }
                    }

                    includeRvMapel.rvRvlayoutContainer.apply {
                        val adapter = RvAdapter<Mapel>("mapel-raport-orangtua", OperationsTypeRv.READ, mainNavController)
                        adapter.setData(dataMapel)
                        adapter.notifyDataSetChanged()
                        this.adapter = adapter
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                } else {
                    requireContext().showToast(it.message())
                }
            }
        }
    }

    fun viewAsGuru(){
        with(binding){
            kelasViewModel.getClassByIdGuru(session.token.toString(), session.id.toString()).observe(viewLifecycleOwner){
                if (it.isSuccessful){
                    mtvRaportMapel.text = it.body()?.data!!.mapelDetail?.size.toString()
                    mtvRaportSiswa.text = it.body()?.data!!.siswaId?.size.toString()
                    mtvRaportTitletotal.text = "Total Mapel & Siswa ${it.body()?.data!!.name}"
                    classId = it.body()?.data!!.id.toString()

                    it.body()?.data!!.mapelDetail?.forEach { mapel->
                        if (mapel!=null){
                            dataMapel.add(mapel)
                        }
                    }

                    includeRvMapel.rvRvlayoutContainer.apply {
                        val adapter = RvAdapter<Mapel>("mapel-raport", OperationsTypeRv.READ, mainNavController)
                        adapter.setData(dataMapel)
                        adapter.notifyDataSetChanged()
                        this.adapter = adapter
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                } else {
                    requireContext().showToast(it.message())
                }
            }
        }
    }

    @Subscribe
    fun navigateToListRaportFragment(commonParams: CommonParams){
        val bundle = bundleOf()
        bundle.putString(Constanta.CLASS_ID, classId)
        bundle.putString(Constanta.MAPEL_ID, commonParams.id)
        bundle.putString(Constanta.MAPEL_NAME, commonParams.username)
        mainNavController.navigate(R.id.action_baseFragment_to_listRaportFragment, bundle)
    }

    @Subscribe
    fun navigateToDetailRaportFragment(eventToDetailRaport: EventToDetailRaport){
        val bundle = bundleOf()
        bundle.putString(Constanta.SISWA_ID, siswaId)
        bundle.putString(Constanta.CLASS_ID, classId)
        bundle.putString(Constanta.MAPEL_ID, eventToDetailRaport.id)
        bundle.putString(Constanta.MAPEL_NAME, eventToDetailRaport.username)
        bundle.putString(Constanta.ROLE, role)
        mainNavController.navigate(R.id.action_baseFragment_to_detailRaportFragment, bundle)
    }

}