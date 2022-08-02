package id.deval.raport.ui.akun.guru

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentRegistrasiGuruBinding
import id.deval.raport.db.models.Account
import id.deval.raport.ui.RvAdapter
import id.deval.raport.utils.DummyData
import id.deval.raport.utils.HelperView
import id.deval.raport.utils.OperationsTypeRv
import id.deval.raport.utils.hide

class RegistrasiGuruFragment : Fragment() {

    private lateinit var _binding: FragmentRegistrasiGuruBinding
    private val binding get() = _binding
    private var dataGuru = arrayListOf<Account>()
    private lateinit var navController: NavController

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
            mtvRegistrasiguruGuru.text = dataGuru.size.toString()

            navController = HelperView.getMainNavController(requireActivity())
            mbRegistrasiguruAdd.setOnClickListener {
                navController.navigate(R.id.action_registrasiGuruFragment_to_addGuruFragment)
            }

            ivRegistrasiguruBack.setOnClickListener {
                navController.popBackStack()
            }

            includeRvGuru.mtvRvlayoutAdd.hide()
            includeRvGuru.mtvRvlayoutViewmore.hide()
            includeRvGuru.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Account>("guru", OperationsTypeRv.EDIT)
                adapter.setData(dataGuru)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

}