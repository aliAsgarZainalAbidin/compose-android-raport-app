package id.deval.raport.ui.akun.guru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentRegistrasiGuruBinding
import id.deval.raport.db.models.Account
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*

class RegistrasiGuruFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentRegistrasiGuruBinding
    private val binding get() = _binding
    private var dataGuru = arrayListOf<Account>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrasiGuruBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataGuru = DummyData().setDummyGuru()
        with(binding) {
            accountViewModel.getAllTeacher(session.token.toString()).observe(viewLifecycleOwner){
                dataGuru = it.data
                mtvRegistrasiguruGuru.text = it.data.size.toString()

                includeRvGuru.mtvRvlayoutAdd.invisible()
                includeRvGuru.mtvRvlayoutViewmore.hide()
                includeRvGuru.rvRvlayoutContainer.apply {
                    val adapter = RvAdapter<Account>("guru", OperationsTypeRv.EDIT,mainNavController)
                    adapter.setData(dataGuru)
                    adapter.notifyDataSetChanged()
                    this.adapter = adapter
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }

            mbRegistrasiguruAdd.setOnClickListener {
                mainNavController.navigate(R.id.action_registrasiGuruFragment_to_addGuruFragment)
            }

            ivRegistrasiguruBack.setOnClickListener {
                mainNavController.popBackStack()
            }


        }
    }

}