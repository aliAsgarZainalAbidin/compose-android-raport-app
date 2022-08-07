package id.deval.raport.ui.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import id.deval.raport.R
import id.deval.raport.databinding.FragmentLoginBinding
import id.deval.raport.utils.*

class LoginFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding
    private var isValid = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            mbLoginLogin.setOnClickListener {
                val username = tietLoginUsername.text.toString().trim().lowercase()

                if (username.isEmpty()) {
                    tietLoginUsername.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (username.isNotEmpty()) {
                    isValid = true
                }
                if (isValid) {
                    val bundle = bundleOf()
                    bundle.putString(Constanta.USERNAME, username)
                    mainNavController.navigate(R.id.action_loginFragment_to_baseFragment, bundle)

                }

            }
        }
    }

}