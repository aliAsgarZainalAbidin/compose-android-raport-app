package id.deval.raport.ui.raport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentListRaportBinding
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.DummyData
import id.deval.raport.utils.OperationsTypeRv
import id.deval.raport.utils.invisible

class ListRaportFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentListRaportBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListRaportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            includeListraportContainer.mtvRvlayoutTitle.text = "Siswa"
            includeListraportContainer.mtvRvlayoutTitle.setTextColor(resources.getColor(R.color.white))
            includeListraportContainer.mtvRvlayoutAdd.invisible()
            includeListraportContainer.mtvRvlayoutViewmore.invisible()

            includeListraportContainer.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Siswa>("siswa-raport", OperationsTypeRv.READ, mainNavController)
                adapter.setData(DummyData().setDummyDataSiswa())
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

}