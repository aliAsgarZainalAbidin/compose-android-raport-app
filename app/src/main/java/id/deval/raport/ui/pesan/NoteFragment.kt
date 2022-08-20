package id.deval.raport.ui.pesan

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentNoteBinding
import id.deval.raport.db.models.Note
import id.deval.raport.ui.adapter.RvNoteAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.showToast

class NoteFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentNoteBinding
    private val binding get() = _binding
    private lateinit var listNote : ArrayList<Note>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val siswaId = arguments?.getString(Constanta.ID)
        var pesanId = arguments?.getString(Constanta.PESAN_ID) ?: ""
        listNote = arrayListOf()
        with(binding) {
            if (siswaId != null) {
                pesanViewModel.getPesanById(session.token.toString(), siswaId)
                    .observe(viewLifecycleOwner) {
                        if (it.isSuccessful) {
                            Log.d(
                                ContentValues.TAG,
                                "onViewCreated: LIST NOTE ${it.body()?.data!!.noteId}"
                            )

                            it.body()?.data!!.noteDetail?.forEach { note ->
                                if (note != null) {
                                    listNote.add(note)
                                }
                            }

                            refreshRVNote()
                            pesanId = it.body()?.data!!.id.toString()
                        } else {
                            requireContext().showToast(it.message())
                        }
                    }
            }

            mtvAddpesanAddnote.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString(Constanta.ID, pesanId)
                mainNavController.navigate(R.id.action_addPesanFragment_to_addNoteFragment, bundle)
            }
        }

    }

    fun refreshRVNote() {
        with(binding) {
            rvAddpesanNote.apply {
                val adapter = RvNoteAdapter(listNote)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
            }
        }
    }
}