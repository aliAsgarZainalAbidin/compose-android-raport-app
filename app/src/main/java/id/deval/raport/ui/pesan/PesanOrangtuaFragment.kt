package id.deval.raport.ui.pesan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentPesanOrangtuaBinding
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.ui.adapter.RvGrowthAdapter
import id.deval.raport.ui.adapter.RvNoteAdapter
import id.deval.raport.utils.*

class PesanOrangtuaFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentPesanOrangtuaBinding
    private val binding get() = _binding

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

        with(binding){
            ivPesanorangtuaPerson.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString(Constanta.ROLE, "orangtua")
                mainNavController.navigate(R.id.action_baseFragment_to_addSiswaFragment, bundle)
            }

            mtvPesanorangtuaMapel.text = dataMapel.size.toString()
            mtvPesanorangtuaSiswa.text = dataSiswa.size.toString()

            rvPesanorangtuaGrowth.apply {
                val adapter = RvGrowthAdapter(DummyData().setDummyDataGrowth())
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            rvPesanorangtuaNote.apply {
                val adapter = RvNoteAdapter(DummyData().setDummyDataNote())
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

}