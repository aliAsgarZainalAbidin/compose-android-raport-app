package id.deval.raport.ui.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddNoteBinding
import id.deval.raport.utils.BaseSkeletonFragment

class AddNoteFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentAddNoteBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            ivAddnoteBack.setOnClickListener {
                mainNavController.popBackStack()
            }

            mbAddnoteSimpan.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }

}