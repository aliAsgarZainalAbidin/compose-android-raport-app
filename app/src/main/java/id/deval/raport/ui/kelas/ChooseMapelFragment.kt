package id.deval.raport.ui.kelas

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.databinding.FragmentChooseMapelBinding
import id.deval.raport.db.event.CommonParamsAdd
import id.deval.raport.db.event.CommonParamsDelete
import id.deval.raport.db.event.LocalDatabaseEvent
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.db.models.request.UpdateMapel
import id.deval.raport.ui.adapter.RvChooseMapelAdapter
import id.deval.raport.ui.adapter.RvChooseSiswaAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.showToast
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

        val idClass = arguments?.getString(Constanta.CLASS_ID)
        requireActivity().getOnBackPressedDispatcher()
            .addCallback(object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        // Handle back press
                        mainNavController.popBackStack()
                    } else {
                        // If you want to get default implementation of onBackPressed, use this
                        this.remove();
                        requireActivity().onBackPressed();
                    }
                }

            });

        dataMapel = arrayListOf()
        with(binding) {
            ivChoosemapelBack.setOnClickListener {
                mainNavController.popBackStack()
            }

            mapelViewModel.getAllLocalMapel().observe(viewLifecycleOwner) {
                dataMapel.addAll(it)
                mapelViewModel.getAllMapel(session.token.toString()).observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        refreshRecyclerViewSiswa(it.body()?.data!!)
                    } else {
                        requireContext().showToast(it.message())
                    }
                }
            }

            mbChoosemapelSimpan.setOnClickListener {
                val listMapel = arrayListOf<String>()
                mapelViewModel.getAllLocalMapel().observe(viewLifecycleOwner) {
                    listMapel.clear()
                    it.forEach {
                        listMapel.add(it.id)
                    }
                    val updateMapel = UpdateMapel(listMapel)
                    kelasViewModel.updateMapelInClassById(
                        session.token.toString(),
                        idClass.toString(),
                        updateMapel
                    ).observe(viewLifecycleOwner) {
                        if (it.isSuccessful) {
                            mainNavController.popBackStack()
                        } else {
                            requireContext().showToast(it.message())
                        }
                    }
                }
            }
        }
    }

    @Subscribe
    fun addOrDeleteLocalMapel(localDatabaseEvent: LocalDatabaseEvent<Mapel>) {
        when (localDatabaseEvent.type) {
            "add" -> {
                mapelViewModel.insertLocalMapel(localDatabaseEvent.data)
                dataMapel.add(localDatabaseEvent.data)
            }
            "delete" -> {
                mapelViewModel.deleteLocalMapel(localDatabaseEvent.data)
                dataMapel.remove(localDatabaseEvent.data)
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