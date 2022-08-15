package id.deval.raport.ui.raport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentListRaportBinding
import id.deval.raport.db.event.CommonParams
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*
import org.greenrobot.eventbus.Subscribe

class ListRaportFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentListRaportBinding
    private val binding get() = _binding
    private var classId = ""
    private var mapelId = ""
    private var mapelName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListRaportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        classId = arguments?.getString(Constanta.CLASS_ID) ?: ""
        mapelId = arguments?.getString(Constanta.MAPEL_ID) ?: ""
        mapelName = arguments?.getString(Constanta.MAPEL_NAME) ?: ""
        binding.mtvListraportName.text = mapelName
        viewAsGuru()
    }

    fun viewAsGuru() {
        with(binding) {
            includeListraportContainer.mtvRvlayoutTitle.text = "Siswa"
            includeListraportContainer.mtvRvlayoutTitle.setTextColor(resources.getColor(R.color.white))
            includeListraportContainer.mtvRvlayoutAdd.invisible()
            includeListraportContainer.mtvRvlayoutViewmore.invisible()

            kelasViewModel.getClassById(session.token.toString(), classId)
                .observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        val rawList = it.body()?.data!![0].siswaDetail
                        val listSiswa = arrayListOf<Siswa>()
                        rawList?.forEach { siswa ->
                            if (siswa != null) {
                                listSiswa.add(siswa)
                            }
                        }
                        includeListraportContainer.rvRvlayoutContainer.apply {
                            val adapter = RvAdapter<Siswa>(
                                "siswa-raport",
                                OperationsTypeRv.READ,
                                mainNavController
                            )
                            adapter.setData(listSiswa)
                            adapter.notifyDataSetChanged()
                            this.adapter = adapter
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        }
                    } else {
                        requireContext().showToast(it.message())
                    }
                }
        }
    }

    @Subscribe
    fun navigateToDetailRaportFragment(commonParams: CommonParams) {
        val bundle = bundleOf()
        bundle.putString(Constanta.MAPEL_ID, mapelId)
        bundle.putString(Constanta.CLASS_ID, classId)
        bundle.putString(Constanta.SISWA_ID, commonParams.id)
        bundle.putString(Constanta.ROLE, "guru")
        mainNavController.navigate(R.id.action_listRaportFragment_to_detailRaportFragment, bundle)
    }

}