package id.deval.raport.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deval.raport.R
import id.deval.raport.databinding.FragmentBaseBinding

class BaseFragment : Fragment() {

    private lateinit var _binding : FragmentBaseBinding
    private val binding get () = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

}