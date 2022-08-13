package id.deval.raport.ui.kelas

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.databinding.FragmentChooseMapelBinding
import id.deval.raport.db.event.CommonParamsAdd
import id.deval.raport.db.event.CommonParamsDelete
import id.deval.raport.db.event.LocalDatabaseEvent
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.adapter.RvChooseMapelAdapter
import id.deval.raport.ui.adapter.RvChooseSiswaAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import org.greenrobot.eventbus.Subscribe

class ChooseMapelFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentChooseMapelBinding
    private val binding get() = _binding
    private lateinit var dataMapel: ArrayList<Mapel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseMapelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataMapel = arrayListOf()
        with(binding) {
            ivChoosemapelBack.setOnClickListener {
                mainNavController.popBackStack()
            }

            mapelViewModel.getAllLocalMapel().observe(viewLifecycleOwner){
                dataMapel.addAll(it)
                mapelViewModel.getAllMapel(session.token.toString()).observe(viewLifecycleOwner) {
                    refreshRecyclerViewSiswa(it.data)
                }
            }

            mbChoosemapelSimpan.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }

    @Subscribe
    fun addOrDeleteLocalMapel(localDatabaseEvent: LocalDatabaseEvent<Mapel>) {
        when (localDatabaseEvent.type) {
            "add" -> {
                mapelViewModel.insertLocalMapel(localDatabaseEvent.data)
            }
            "delete" -> {
                mapelViewModel.deleteLocalMapel(localDatabaseEvent.data)
            }
        }
        mapelViewModel.getAllLocalMapel().observe(viewLifecycleOwner) {
            it.forEach {
                Log.d(ContentValues.TAG, "addOrDeleteLocalSiswa: ${it.name}")
            }
        }
    }

    fun refreshRecyclerViewSiswa(data: ArrayList<Mapel>) {
        with(binding) {
            rvChoosemapelContainer.apply {
                val adapter = RvChooseMapelAdapter<Mapel>(dataMapel)
                adapter.setData(data)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL, false
                )
            }
        }
    }
}