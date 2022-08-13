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
import id.deval.raport.utils.*

class ListRaportFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentListRaportBinding
    private val binding get() = _binding
    private var classId = ""
    private var mapelId = ""

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
        viewAsGuru()
    }

    fun viewAsGuru(){
        with(binding){
            includeListraportContainer.mtvRvlayoutTitle.text = "Siswa"
            includeListraportContainer.mtvRvlayoutTitle.setTextColor(resources.getColor(R.color.white))
            includeListraportContainer.mtvRvlayoutAdd.invisible()
            includeListraportContainer.mtvRvlayoutViewmore.invisible()

            kelasViewModel.getClassById(session.token.toString(), classId).observe(viewLifecycleOwner){
                val rawList = it.data[0].siswaDetail
                val listSiswa = arrayListOf<Siswa>()
                rawList?.forEach { siswa->
                    if (siswa!=null){
                        listSiswa.add(siswa)
                    }
                }
                includeListraportContainer.rvRvlayoutContainer.apply {
                    val adapter = RvAdapter<Siswa>("siswa-raport", OperationsTypeRv.READ, mainNavController)
                    adapter.setData(listSiswa)
                    adapter.notifyDataSetChanged()
                    this.adapter = adapter
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

}