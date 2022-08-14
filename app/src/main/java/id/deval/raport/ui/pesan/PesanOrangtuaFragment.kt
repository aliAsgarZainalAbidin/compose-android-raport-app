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
import id.deval.raport.databinding.FragmentPesanOrangtuaBinding
import id.deval.raport.db.models.Growth
import id.deval.raport.db.models.Note
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.ui.adapter.RvGrowthAdapter
import id.deval.raport.ui.adapter.RvNoteAdapter
import id.deval.raport.utils.*

class PesanOrangtuaFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentPesanOrangtuaBinding
    private val binding get() = _binding
    private lateinit var listGrowth : ArrayList<Growth>
    private lateinit var listNote : ArrayList<Note>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPesanOrangtuaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataMapel = DummyData().setDummyDataMapel()
        val dataSiswa = DummyData().setDummyDataSiswa()

        listGrowth = arrayListOf()
        listNote = arrayListOf()
        var siswaId = ""
        with(binding){
            ivPesanorangtuaPerson.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString(Constanta.ROLE, "orangtua")
                mainNavController.navigate(R.id.action_baseFragment_to_addSiswaFragment, bundle)
            }

            mtvPesanorangtuaName.text = session.name

            siswaViewModel.getSiswaByNIS(session.token.toString(), session.username.toString()).observe(viewLifecycleOwner){
                siswaId = it.data.id

                pesanViewModel.getPesanById(session.token.toString(), siswaId).observe(viewLifecycleOwner){
                    it.data.growthDetail?.forEach { growth->
                        if (growth != null){
                            listGrowth.add(growth)
                        }
                    }

                    Log.d(ContentValues.TAG, "onViewCreated: LIST GROWTH ${it.data.growthId}")
                    Log.d(ContentValues.TAG, "onViewCreated: LIST NOTE ${it.data.noteId}")

                    it.data.noteDetail?.forEach { note ->
                        if (note!=null){
                            listNote.add(note)
                        }
                    }

                    rvPesanorangtuaGrowth.apply {
                        val adapter = RvGrowthAdapter(listGrowth)
                        adapter.notifyDataSetChanged()
                        this.adapter = adapter
                        layoutManager =
                            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
                    }

                    rvPesanorangtuaNote.apply {
                        val adapter = RvNoteAdapter(listNote)
                        adapter.notifyDataSetChanged()
                        this.adapter = adapter
                        layoutManager =
                            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
                    }
                }
            }


        }
    }

}