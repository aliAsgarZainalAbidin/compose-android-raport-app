package id.deval.raport.ui.kelas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentKelasBinding
import id.deval.raport.db.event.CommonParams
import id.deval.raport.db.models.Kelas
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*
import org.greenrobot.eventbus.Subscribe

class KelasFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentKelasBinding
    private val binding get() = _binding
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

            mtvKelasName.text = session.name
            includeRvGuru.mtvRvlayoutTitle.text = "Kelas"
            includeRvGuru.mtvRvlayoutAdd.show()
            includeRvGuru.mtvRvlayoutAdd.setOnClickListener {
                mainNavController.navigate(R.id.action_baseFragment_to_addKelasFragment)
            }
            includeRvGuru.mtvRvlayoutAdd.text = "Tambah Kelas"
            includeRvGuru.mtvRvlayoutViewmore.hide()

            refreshRecyclerViewKelas()
        }
    }

    fun refreshRecyclerViewKelas() {
        with(binding) {
            kelasViewModel.getAllClass(session.token.toString()).observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    mtvKelasTotalKelas.text = it.body()?.data!!.size.toString()
                    includeRvGuru.rvRvlayoutContainer.apply {
                        val adapter =
                            RvAdapter<Kelas>("kelas", OperationsTypeRv.EDIT, mainNavController)
                        adapter.setData(it.body()?.data!!)
                        adapter.notifyDataSetChanged()
                        this.adapter = adapter
                        layoutManager =
                            LinearLayoutManager(
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
    override fun deleteItem(commonParams: CommonParams) {
        super.deleteItem(commonParams)
        kelasViewModel.deleteClass(session.token.toString(), commonParams.id)
            .observe(viewLifecycleOwner) {
                refreshRecyclerViewKelas()
            }
    }
}