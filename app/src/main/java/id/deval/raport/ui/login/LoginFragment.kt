package id.deval.raport.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import id.deval.raport.R
import id.deval.raport.databinding.FragmentLoginBinding
import id.deval.raport.utils.HelperView

class LoginFragment : Fragment() {

    private lateinit var _binding : FragmentLoginBinding
    private lateinit var navController: NavController
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = HelperView.getMainNavController(requireActivity())
        with(binding){
            mbLoginLogin.setOnClickListener {
                navController.navigate(R.id.action_loginFragment_to_baseFragment)
            }
        }
    }

}