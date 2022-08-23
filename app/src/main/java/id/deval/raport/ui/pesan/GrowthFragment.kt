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
import id.deval.raport.databinding.FragmentGrowthBinding
import id.deval.raport.db.models.Growth
import id.deval.raport.ui.adapter.RvGrowthAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.showToast

class GrowthFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentGrowthBinding
    private val binding get() = _binding
    private lateinit var listGrowth : ArrayList<Growth>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGrowthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var pesanId = arguments?.getString(Constanta.PESAN_ID)
        val siswaId = arguments?.getString(Constanta.ID) ?: ""
        with(binding){
            listGrowth = arrayListOf()
            mtvAddpesanAddgrowth.setOnClickListener {
                try {
                    val bundle = bundleOf()
                    bundle.putString(Constanta.ID, pesanId)
                    mainNavController.navigate(R.id.action_addPesanFragment_to_addGrowthFragment, bundle)
                }catch (e: Exception){
                    Log.d(ContentValues.TAG, "onViewCreated: $e")
                }
            }

            pesanViewModel.getPesanById(session.token.toString(), siswaId).observe(viewLifecycleOwner){
                if (it.isSuccessful){
                    it.body()?.data!!.growthDetail?.forEach { growth->
                        if (growth != null){
                            listGrowth.add(growth)
                        }
                    }

                    refreshRVGrowth()
                    pesanId = it.body()?.data!!.id.toString()
                } else {
                    requireContext().showToast(it.message())
                }
            }

            refreshRVGrowth()
        }
    }

    fun refreshRVGrowth(){
        with(binding){
            rvAddpesanGrowth.apply {
                val adapter = RvGrowthAdapter(listGrowth)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
            }
        }
    }

}