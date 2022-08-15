package id.deval.raport.ui.akun.siswa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentRegistrasiSiswaBinding
import id.deval.raport.db.event.CommonParams
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*
import org.greenrobot.eventbus.Subscribe

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
                mainNavController.navigate(
                    R.id.action_registrasiSiswaFragment_to_addSiswaFragment,
                    bundle
                )
            }

            ivRegistrasisiswaBack.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }

    fun refreshRecyclerViewSiswa() {
        with(binding) {
            siswaViewModel.getAllSiswa(session.token.toString()).observe(viewLifecycleOwner) {
                if (it.isSuccessful){
                    mtvRegistrasisiswaGuru.text = it.body()?.data!!.size.toString()
                    includeRvGuru.rvRvlayoutContainer.apply {
                        val adapter =
                            RvAdapter<Siswa>("siswa", OperationsTypeRv.EDIT, mainNavController)
                        adapter.setData(it.body()?.data!!)
                        adapter.notifyDataSetChanged()
                        this.adapter = adapter
                        layoutManager =
                            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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
        accountViewModel.deleteAccountByUsername(
            session.token.toString(),
            commonParams.username.toString()
        ).observe(viewLifecycleOwner) {
            Log.d("TAG", "deleteItem: ${commonParams.id}")
            siswaViewModel.deleteSiswaById(session.token.toString(), commonParams.id)
                .observe(viewLifecycleOwner) {
                    refreshRecyclerViewSiswa()
                }
        }
    }

}