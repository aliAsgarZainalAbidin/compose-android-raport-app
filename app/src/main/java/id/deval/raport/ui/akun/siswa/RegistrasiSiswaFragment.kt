package id.deval.raport.ui.akun.siswa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentRegistrasiSiswaBinding
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*

class RegistrasiSiswaFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentRegistrasiSiswaBinding
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

        with(binding) {
            includeRvGuru.mtvRvlayoutTitle.text = "Siswa"
            includeRvGuru.mtvRvlayoutViewmore.hide()
            includeRvGuru.mtvRvlayoutAdd.invisible()
            refreshRecyclerViewSiswa()

            mbRegistrasisiswaAdd.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString(Constanta.ROLE, "admin")
                mainNavController.navigate(R.id.action_registrasiSiswaFragment_to_addSiswaFragment, bundle)
            }

            ivRegistrasisiswaBack.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }

    fun refreshRecyclerViewSiswa() {
        with(binding) {
            siswaViewModel.getAllSiswa(session.token.toString()).observe(viewLifecycleOwner) {
                mtvRegistrasisiswaGuru.text = it.data.size.toString()
                includeRvGuru.rvRvlayoutContainer.apply {
                    val adapter =
                        RvAdapter<Siswa>("siswa", OperationsTypeRv.EDIT, mainNavController)
                    adapter.setData(it.data)
                    adapter.notifyDataSetChanged()
                    this.adapter = adapter
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

}