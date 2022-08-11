package id.deval.raport.ui.kelas

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.databinding.FragmentChooseSiswaBinding
import id.deval.raport.db.event.CommonParamsAdd
import id.deval.raport.db.event.CommonParamsDelete
import id.deval.raport.db.models.Siswa
import id.deval.raport.db.models.StringSiswa
import id.deval.raport.ui.adapter.RvChooseSiswaAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import org.greenrobot.eventbus.Subscribe

class ChooseSiswaFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentChooseSiswaBinding
    private lateinit var dataSiswa: ArrayList<StringSiswa>
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

        dataSiswa = arrayListOf()
        stringSiswaViewModel.getAllSiswa().observe(viewLifecycleOwner){
            dataSiswa.addAll(it)
        }
        with(binding) {
            ivChoosesiswaBack.setOnClickListener {
                mainNavController.popBackStack()
            }
            siswaViewModel.getAllSiswa(session.token.toString()).observe(viewLifecycleOwner) {
                refreshRecyclerViewSiswa(it.data)
            }

            mbChoosesiswaSimpan.setOnClickListener {
                mainNavController.popBackStack()
            }
        }
    }

    @Subscribe
    fun addToListSiswa(commonParamsAdd: CommonParamsAdd){
        val stringSiswa = StringSiswa(commonParamsAdd.id)
        dataSiswa.add(stringSiswa)
        stringSiswaViewModel.insertAllSiswa(dataSiswa)
        Log.d(TAG, "addToListSiswa: $dataSiswa")
    }

    @Subscribe
    fun deleteFromListSiswa(commonParamsDelete: CommonParamsDelete){
        val stringSiswa = StringSiswa(commonParamsDelete.id)
        dataSiswa.forEachIndexed { index, s ->
            if (s.id == commonParamsDelete.id){
                dataSiswa.removeAt(index)
                stringSiswaViewModel.deleteSiswa(stringSiswa)
            }
        }
        Log.d(TAG, "addToListSiswa: $dataSiswa")
    }

    fun refreshRecyclerViewSiswa(data: ArrayList<Siswa>) {
        with(binding) {
            rvChoosesiswaContainer.apply {
                val adapter = RvChooseSiswaAdapter<Siswa>(dataSiswa)
                adapter.setData(data)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

}