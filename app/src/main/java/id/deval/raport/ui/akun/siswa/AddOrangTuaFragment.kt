package id.deval.raport.ui.akun.siswa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddOrangTuaBinding
import id.deval.raport.utils.BaseSkeletonFragment

class AddOrangTuaFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAddOrangTuaBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrangTuaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            ivAddorangtuaBack.setOnClickListener {
                mainNavController.popBackStack()
            }
            mbAddorangtuaSimpan.setOnClickListener {
                mainNavController.popBackStack(R.id.addSiswaFragment, true)
            }
        }
    }

}