package id.deval.raport.ui.kelas

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentChooseSiswaBinding
import id.deval.raport.db.event.CommonParamsAdd
import id.deval.raport.db.event.CommonParamsDelete
import id.deval.raport.db.event.LocalDatabaseEvent
import id.deval.raport.db.models.Siswa
import id.deval.raport.db.models.request.UpdateSiswa
import id.deval.raport.ui.adapter.RvChooseSiswaAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.showToast
import org.greenrobot.eventbus.Subscribe

class ChooseSiswaFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentChooseSiswaBinding
    private lateinit var dataSiswa: ArrayList<Siswa>
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseSiswaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        Log.d(TAG, "onViewCreated: IM HERE")
        val idClass = arguments?.getString(Constanta.CLASS_ID)
        dataSiswa = arrayListOf()
        with(binding) {
            ivChoosesiswaBack.setOnClickListener {
                mainNavController.popBackStack()
            }

            if (idClass != null) {
                siswaViewModel.getAllLocalSiswa()
                    .observe(viewLifecycleOwner) {
                        dataSiswa.clear()
                        it.forEach {
                            dataSiswa.add(it)
                        }
                        siswaViewModel.getAllSiswa(session.token.toString())
                            .observe(viewLifecycleOwner) {
                                if (it.isSuccessful) {
                                    refreshRecyclerViewSiswa(it.body()?.data!!, dataSiswa)
                                } else {
                                    requireContext().showToast(it.message())
                                }
                            }
                    }
            }

            mbChoosesiswaSimpan.setOnClickListener {
                val listSiswa = arrayListOf<String>()
                siswaViewModel.getAllLocalSiswa()
                    .observe(viewLifecycleOwner) {
                        listSiswa.clear()
                        it.forEach {
                            listSiswa.add(it.id)
                        }
                    }
                val updateSiswa = UpdateSiswa(listSiswa)
                kelasViewModel.updateSiswaInClassById(
                    session.token.toString(),
                    idClass.toString(),
                    updateSiswa
                ).observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        Log.d(TAG, "onViewCreated: CHOOSE SISWA ${it?.body()?.data?.siswaId}")
                        mainNavController.popBackStack()
                    } else {
                        requireContext().showToast(it.message())
                    }
                }
            }
        }
    }

    @Subscribe
    fun addOrDeleteLocalSiswa(localDatabaseEvent: LocalDatabaseEvent<Siswa>) {
        Log.d(TAG, "addOrDeleteLocalSiswa: ${dataSiswa.size}")

        when (localDatabaseEvent.type) {
            "add" -> {
                siswaViewModel.insertLocalSiswa(localDatabaseEvent.data)
                dataSiswa.add(localDatabaseEvent.data)
            }
            "delete" -> {
                siswaViewModel.deleteLocalSiswa(localDatabaseEvent.data)
                dataSiswa.remove(localDatabaseEvent.data)
            }
        }
    }

    fun refreshRecyclerViewSiswa(data: ArrayList<Siswa>, localData: ArrayList<Siswa>) {
        with(binding) {
            rvChoosesiswaContainer.apply {
                val adapter = RvChooseSiswaAdapter<Siswa>(localData)
                adapter.setData(data)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

}