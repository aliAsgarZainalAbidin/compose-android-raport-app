package id.deval.raport.ui.pesan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deval.raport.R
import id.deval.raport.databinding.FragmentNoteBinding
import id.deval.raport.utils.BaseSkeletonFragment

class NoteFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentNoteBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }
}