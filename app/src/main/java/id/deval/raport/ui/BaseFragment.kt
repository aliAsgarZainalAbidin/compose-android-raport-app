package id.deval.raport.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import id.deval.raport.R
import id.deval.raport.databinding.FragmentBaseBinding
import id.deval.raport.utils.HelperView

class BaseFragment : Fragment() {

    private lateinit var _binding : FragmentBaseBinding
    private val binding get () = _binding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = HelperView.getSecNavController(this)

        with(binding){
            botnavBaseContainer.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.akun -> {
                        navController.navigate(R.id.akunFragment)
                        true
                    }
                    R.id.kelas -> {
                        navController.navigate(R.id.kelasFragment)
                        true
                    }
                    R.id.mapel -> {
                        navController.navigate(R.id.mapelFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }

}