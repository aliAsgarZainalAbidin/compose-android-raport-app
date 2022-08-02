package id.deval.raport.ui.akun

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAkunBinding
import id.deval.raport.db.models.Account
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.RvAdapter
import id.deval.raport.utils.*

class AkunFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAkunBinding
    private val binding get() = _binding
    private var dataGuru = arrayListOf<Account>()
    private var dataSiswa = arrayListOf<Siswa>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataGuru = DummyData().setDummyGuru()
        dataSiswa = DummyData().setDummyDataSiswa()

        with(binding) {
            mtvAkunName.text = "Admin"
            with(cvAkunContainer){
                mtvAkunGuru.text = dataGuru.size.toString()
                mtvAkunSiswa.text = dataSiswa.size.toString()
            }

            includeRvGuru.mtvRvlayoutViewmore.show()
            includeRvGuru.mtvRvlayoutViewmore.setOnClickListener {
                mainNavController.navigate(R.id.action_baseFragment_to_registrasiGuruFragment)
            }
            includeRvGuru.mtvRvlayoutAdd.hide()
            includeRvGuru.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Account>("guru", OperationsTypeRv.READ, 2)
                adapter.setData(dataGuru)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            includeRvSiswa.mtvRvlayoutViewmore.show()
            includeRvSiswa.mtvRvlayoutViewmore.setOnClickListener {
                mainNavController.navigate(R.id.action_baseFragment_to_registrasiSiswaFragment)
            }
            includeRvSiswa.mtvRvlayoutAdd.hide()
            includeRvSiswa.mtvRvlayoutTitle.text = "Siswa"
            includeRvSiswa.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Siswa>("siswa", OperationsTypeRv.READ, 2)
                adapter.setData(dataSiswa)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }



}