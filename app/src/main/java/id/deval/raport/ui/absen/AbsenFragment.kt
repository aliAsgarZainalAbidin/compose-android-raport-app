package id.deval.raport.ui.absen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAbsenBinding
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*

class AbsenFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAbsenBinding
    private val binding get() = _binding
    private lateinit var dataMapel: ArrayList<Mapel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAbsenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val role = arguments?.getString(Constanta.ROLE).toString()

//        dataMapel = DummyData().setDummyDataMapel()
        dataMapel = arrayListOf()
        with(binding) {
            mtvAbsenName.text = session.name
            includeRvMapel.rvRvlayoutContainer.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

            kelasViewModel.getClassByIdGuru(session.token.toString(), session.id.toString())
                .observe(viewLifecycleOwner) {
                    mtvAbsenMapel.text = it.data.mapelDetail?.size.toString()
                    mtvAbsenSiswa.text = it.data.siswaId?.size.toString()

                    it.data.mapelDetail?.forEach { mapel->
                        if (mapel!=null){
                            dataMapel.add(mapel)
                        }
                    }

                    when (role) {
                        "guru" -> {
                            includeRvMapel.rvRvlayoutContainer.apply {
                                val adapter = RvAdapter<Mapel>(
                                    "mapel-absen",
                                    OperationsTypeRv.READ,
                                    mainNavController
                                )
                                adapter.setData(dataMapel)
                                adapter.notifyDataSetChanged()
                                this.adapter = adapter
                            }
                        }
                        "orangtua" -> {
                            ivAbsenPerson.setOnClickListener {
                                val bundle = bundleOf()
                                bundle.putString(Constanta.ROLE, role)
                                mainNavController.navigate(
                                    R.id.action_baseFragment_to_addSiswaFragment,
                                    bundle
                                )
                            }

                            includeRvMapel.rvRvlayoutContainer.apply {
                                val adapter = RvAdapter<Mapel>(
                                    "mapel-absen-orangtua",
                                    OperationsTypeRv.READ,
                                    mainNavController
                                )
                                adapter.setData(dataMapel)
                                adapter.notifyDataSetChanged()
                                this.adapter = adapter
                            }
                        }
                        else -> false
                    }
                }

            includeRvMapel.mtvRvlayoutTitle.text = "Mata Pelajaran"
            includeRvMapel.mtvRvlayoutAdd.invisible()
            includeRvMapel.mtvRvlayoutViewmore.invisible()
        }
    }
}