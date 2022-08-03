package id.deval.raport.ui.mapel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentMapelBinding
import id.deval.raport.db.models.Mapel
import id.deval.raport.ui.RvAdapter
import id.deval.raport.utils.*

class MapelFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentMapelBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataMapel = DummyData().setDummyDataMapel()
        with(binding){
            mtvMapelName.text = "Admin"
            mtvMapelTotalmapel.text = dataMapel.size.toString()
            includeRvMapel.mtvRvlayoutAdd.show()
            includeRvMapel.mtvRvlayoutAdd.text = "Tambah Mapel"
            includeRvMapel.mtvRvlayoutTitle.text = "Mata Pelajaran"
            includeRvMapel.mtvRvlayoutViewmore.hide()
            includeRvMapel.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Mapel>("mapel",OperationsTypeRv.READ)
                adapter.setData(dataMapel)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }
}