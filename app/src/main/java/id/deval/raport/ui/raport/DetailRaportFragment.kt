package id.deval.raport.ui.raport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deval.raport.R
import id.deval.raport.databinding.FragmentDetailRaportBinding
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.invisible
import id.deval.raport.utils.show

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

        with(binding) {
            includeDetailraportContainer.mtvRvitemName.text = "Ali"
            includeDetailraportContainer.mtvRvitemNis.text = "60200117039"
            includeRvSiswa.mtvRvlayoutViewmore.invisible()
            includeRvSiswa.mtvRvlayoutAdd.show()
            includeRvSiswa.mtvRvlayoutAdd.setOnClickListener {
                mainNavController.navigate(R.id.action_detailRaportFragment_to_addTugasFragment)
            }
            includeRvSiswa.mtvRvlayoutTitle.text = "Tugas"
            includeRvSiswa.mtvRvlayoutTitle.setTextColor(resources.getColor(R.color.white))
        }
    }

}