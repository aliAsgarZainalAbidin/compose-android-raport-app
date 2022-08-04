package id.deval.raport.ui.pesan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddPesanBinding
import id.deval.raport.ui.adapter.RvGrowthAdapter
import id.deval.raport.ui.adapter.RvNoteAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.DummyData

class AddPesanFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAddPesanBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPesanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            mtvAddpesanAddgrowth.setOnClickListener {
                TODO("NOT READY")
            }
            rvAddpesanGrowth.apply {
                val adapter = RvGrowthAdapter(DummyData().setDummyDataGrowth())
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            mtvAddpesanAddnote.setOnClickListener {
                TODO()
            }
            rvAddpesanNote.apply {
                val adapter = RvNoteAdapter(DummyData().setDummyDataNote())
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

}