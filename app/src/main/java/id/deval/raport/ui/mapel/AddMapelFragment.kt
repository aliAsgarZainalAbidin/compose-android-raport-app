package id.deval.raport.ui.mapel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddMapelBinding
import id.deval.raport.utils.BaseSkeletonFragment

class AddMapelFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentAddMapelBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddMapelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding){
            ivAddmapelBack.setOnClickListener {
                mainNavController.popBackStack()
            }
            mbAddmapelSimpan.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }

}