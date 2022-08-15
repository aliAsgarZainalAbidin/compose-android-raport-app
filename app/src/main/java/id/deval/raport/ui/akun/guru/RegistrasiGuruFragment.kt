package id.deval.raport.ui.akun.guru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentRegistrasiGuruBinding
import id.deval.raport.db.event.CommonParams
import id.deval.raport.db.models.Account
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*
import org.greenrobot.eventbus.Subscribe

class RegistrasiGuruFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentRegistrasiGuruBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrasiGuruBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            refreshRecyclerViewTeacher()

            mbRegistrasiguruAdd.setOnClickListener {
                mainNavController.navigate(R.id.action_registrasiGuruFragment_to_addGuruFragment)
            }

            ivRegistrasiguruBack.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }

    @Subscribe
    override fun deleteItem(commonParams: CommonParams) {
        accountViewModel.deleteTeacherById(session.token.toString(), commonParams.id).observe(viewLifecycleOwner){
            if (it.isSuccessful){
                requireContext().showToast("Success")
                refreshRecyclerViewTeacher()
            } else {
                requireContext().showToast(it.message())
            }
        }
    }

    fun refreshRecyclerViewTeacher() {
        with(binding) {
            accountViewModel.getAllTeacher(session.token.toString()).observe(viewLifecycleOwner) {
                if (it.isSuccessful){
                    mtvRegistrasiguruGuru.text = it.body()?.data!!.size.toString()

                    includeRvGuru.mtvRvlayoutAdd.invisible()
                    includeRvGuru.mtvRvlayoutViewmore.hide()
                    includeRvGuru.rvRvlayoutContainer.apply {
                        val adapter =
                            RvAdapter<Account>("guru", OperationsTypeRv.EDIT, mainNavController)
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

}