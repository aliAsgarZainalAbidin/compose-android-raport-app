package id.deval.raport.ui.pesan

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddPesanBinding
import id.deval.raport.db.models.Growth
import id.deval.raport.db.models.Note
import id.deval.raport.ui.adapter.RvGrowthAdapter
import id.deval.raport.ui.adapter.RvNoteAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.DummyData

class AddPesanFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAddPesanBinding
    private val binding get() = _binding
    private lateinit var listGrowth : ArrayList<Growth>
    private lateinit var listNote : ArrayList<Note>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPesanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val siswaId = arguments?.getString(Constanta.ID)
        listGrowth = arrayListOf()
        listNote = arrayListOf()
        var pesanId = ""
        with(binding) {
            mtvAddpesanAddgrowth.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString(Constanta.ID, pesanId)
                mainNavController.navigate(R.id.action_addPesanFragment_to_addGrowthFragment, bundle)
            }

            if (siswaId!=null){
                siswaViewModel.getSiswaById(session.token.toString(), siswaId).observe(viewLifecycleOwner){
                    with(includeAddpesanContainer){
                        mtvRvitemName.setText(it.data.name)
                        mtvRvitemNis.setText(it.data.nis)
                    }
                }

                pesanViewModel.getPesanById(session.token.toString(), siswaId).observe(viewLifecycleOwner){
                    it.data.growthDetail?.forEach { growth->
                        if (growth != null){
                            listGrowth.add(growth)
                        }
                    }

                    Log.d(TAG, "onViewCreated: LIST GROWTH ${it.data.growthId}")
                    Log.d(TAG, "onViewCreated: LIST NOTE ${it.data.noteId}")

                    it.data.noteDetail?.forEach { note ->
                        if (note!=null){
                            listNote.add(note)
                        }
                    }

                    refreshRVGrowth()
                    refreshRVNote()
                    pesanId = it.data.id.toString()
                }
            }

            mtvAddpesanAddnote.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString(Constanta.ID, pesanId)
                mainNavController.navigate(R.id.action_addPesanFragment_to_addNoteFragment, bundle)
            }

        }
    }

    fun refreshRVGrowth(){
        with(binding){
            rvAddpesanGrowth.apply {
                val adapter = RvGrowthAdapter(listGrowth)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    fun refreshRVNote(){
        with(binding){
            rvAddpesanNote.apply {
                val adapter = RvNoteAdapter(listNote)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

}