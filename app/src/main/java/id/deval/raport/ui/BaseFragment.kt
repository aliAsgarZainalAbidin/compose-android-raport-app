package id.deval.raport.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import id.deval.raport.R
import id.deval.raport.databinding.FragmentBaseBinding
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.HelperView

class BaseFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentBaseBinding
    private val binding get () = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            botnavBaseContainer.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.akun -> {
                        secNavController.navigate(R.id.akunFragment)
                        true
                    }
                    R.id.kelas -> {
                        secNavController.navigate(R.id.kelasFragment)
                        true
                    }
                    R.id.mapel -> {
                        secNavController.navigate(R.id.mapelFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }

}