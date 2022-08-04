package id.deval.raport.ui.tugas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddTugasBinding
import id.deval.raport.utils.BaseSkeletonFragment

class AddTugasFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentAddTugasBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTugasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            ivAddtugasBack.setOnClickListener {
                mainNavController.popBackStack()
            }

            mbAddtugasSimpan.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }

}