package id.deval.raport.ui.akun.siswa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentRegistrasiSiswaBinding
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.RvAdapter
import id.deval.raport.utils.*

class RegistrasiSiswaFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentRegistrasiSiswaBinding
    private lateinit var dataSiswa : ArrayList<Siswa>
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrasiSiswaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataSiswa = DummyData().setDummyDataSiswa()
        with(binding){
            includeRvGuru.mtvRvlayoutTitle.text = "Siswa"
            includeRvGuru.mtvRvlayoutViewmore.hide()
            includeRvGuru.mtvRvlayoutAdd.invisible()
            includeRvGuru.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Siswa>("siswa", OperationsTypeRv.EDIT,mainNavController)
                adapter.setData(dataSiswa)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            mbRegistrasisiswaAdd.setOnClickListener {
                mainNavController.navigate(R.id.action_registrasiSiswaFragment_to_addSiswaFragment)
            }

            ivRegistrasisiswaBack.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }

}