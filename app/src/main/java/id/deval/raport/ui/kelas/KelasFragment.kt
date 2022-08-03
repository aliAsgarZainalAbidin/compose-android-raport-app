package id.deval.raport.ui.kelas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentKelasBinding
import id.deval.raport.db.models.Kelas
import id.deval.raport.ui.RvAdapter
import id.deval.raport.utils.*

class KelasFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentKelasBinding
    private val binding get() = _binding
    private lateinit var navController: NavController
    private var dataKelas = arrayListOf<Kelas>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKelasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataKelas = DummyData().setDummyDataKelas()

        with(binding) {

            mtvKelasName.text = "Ibu Ica"
            mtvKelasTotalKelas.text = dataKelas.size.toString()
            includeRvGuru.mtvRvlayoutTitle.text = "Kelas"
            includeRvGuru.mtvRvlayoutAdd.show()
            includeRvGuru.mtvRvlayoutAdd.setOnClickListener {
                mainNavController.navigate(R.id.action_baseFragment_to_addKelasFragment)
            }
            includeRvGuru.mtvRvlayoutAdd.text = "Tambah Kelas"
            includeRvGuru.mtvRvlayoutViewmore.hide()

            includeRvGuru.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Kelas>("kelas", OperationsTypeRv.EDIT,mainNavController)
                adapter.setData(dataKelas)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }
}