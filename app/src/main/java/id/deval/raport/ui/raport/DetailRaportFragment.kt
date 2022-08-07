package id.deval.raport.ui.raport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentDetailRaportBinding
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
        with(binding) {
            includeDetailraportContainer.mtvRvitemName.text = "Ali"
            includeDetailraportContainer.mtvRvitemNis.text = "60200117039"
            includeRvSiswa.mtvRvlayoutTitle.text = "Tugas"
            includeRvSiswa.mtvRvlayoutTitle.setTextColor(resources.getColor(R.color.white))

            when (role) {
                "guru" -> viewAsGuru()
                "orangtua" -> viewAsOrangTua()
            }
        }
    }

    fun viewAsGuru() {
        with(binding) {
            includeRvSiswa.mtvRvlayoutViewmore.invisible()
            includeRvSiswa.mtvRvlayoutAdd.show()
            includeRvSiswa.mtvRvlayoutAdd.setOnClickListener {
                mainNavController.navigate(R.id.action_detailRaportFragment_to_addTugasFragment)
            }

            includeRvSiswa.rvRvlayoutContainer.apply {
                val adapter = RvTugasAdapter(DummyData().setDummyDataTugas(), mainNavController)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    fun viewAsOrangTua() {
        with(binding) {
            includeRvSiswa.mtvRvlayoutViewmore.invisible()
            includeRvSiswa.mtvRvlayoutAdd.invisible()

            tilDetailraportSikap.isEnabled = false
            tilDetailraportKeterampilan.isEnabled = false
            tilDetailraportUts.isEnabled = false
            tilDetailraportUas.isEnabled = false
            tilDetailraportDesc.isEnabled = false

            includeRvSiswa.rvRvlayoutContainer.apply {
                val adapter = RvTugasAdapter(DummyData().setDummyDataTugas(), null)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

}