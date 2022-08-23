package id.deval.raport.ui.absen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAbsenDetailBinding
import id.deval.raport.databinding.RvAbsenSiswaBinding
import id.deval.raport.db.event.CommonParams
import id.deval.raport.db.models.Absen
import id.deval.raport.db.models.request.AttendanceAdd
import id.deval.raport.db.models.request.AttendanceItem
import id.deval.raport.db.models.request.AttendanceUpdate
import id.deval.raport.db.response.DetailSiswaItem
import id.deval.raport.ui.adapter.RvAbsenSiswaAdapter
import id.deval.raport.ui.adapter.RvAbsensiAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.DummyData
import id.deval.raport.utils.showToast
import org.greenrobot.eventbus.Subscribe
import kotlin.math.log

class AbsenDetailFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAbsenDetailBinding
    private val binding get() = _binding
    lateinit var arrayListDetailSiswaItem: ArrayList<DetailSiswaItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAbsenDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayListDetailSiswaItem = arrayListOf()
        val date = arguments?.getString(Constanta.DATE)
        val id = arguments?.getString(Constanta.ID)
        with(binding) {
            mtvDetailabsenDate.text = date
            ivDetailabsenBack.setOnClickListener {
                try {
                    mainNavController.popBackStack()
                } catch (e: Exception){
                    Log.d(TAG, "onViewCreated: $e")
                }
            }

            Log.d("TAG", "onViewCreated: $date $id")

            if (!id.isNullOrEmpty()) {
                refreshRecyclerViewAbsen(id)

                mbDetailabsenAdd.setOnClickListener {
                    absenViewModel.getAllLocalAbsen().observe(viewLifecycleOwner) {
                        val listAttendanceItem = arrayListOf<AttendanceItem>()
                        it.forEach { absen ->
                            val attendanceItem = AttendanceItem(absen.kehadiran, absen.siswaId)
                            listAttendanceItem.add(attendanceItem)
                        }
                        Log.d("TAG", "onViewCreated: $listAttendanceItem")
                        val updateAttendance = AttendanceUpdate(listAttendanceItem)
                        absenViewModel.updateAttendanceById(
                            session.token.toString(),
                            id,
                            updateAttendance
                        )
                            .observe(viewLifecycleOwner) {
                                if (it.isSuccessful) {
                                    try {
                                        requireContext().showToast("${it.body()?.status} men-update data")
                                        mainNavController.popBackStack()
                                    } catch (e : Exception){
                                        Log.d(TAG, "onViewCreated: $e")
                                    }
                                } else {
                                    requireActivity().showToast(it.message())
                                }
                            }
                    }
                }
            }
        }
    }

    fun refreshRecyclerViewAbsen(id: String) {
        with(binding) {
            absenViewModel.getAttendanceById(session.token.toString(), id)
                .observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        val data = it.body()?.data?.getOrNull(0)

                        data?.detailSiswa?.forEachIndexed { i, siswa ->
                            val kehadiran = data.attendance?.find { attendanceItem -> attendanceItem?.siswaId == siswa?.id }
                            val detailSiswaItem = DetailSiswaItem(
                                siswa?.name,
                                siswa?.nis,
                                kehadiran?.kehadiran,
                                siswa?.id
                            )
                            Log.d(
                                "TAG",
                                "refreshRecyclerViewAbsen: DETAIL ${detailSiswaItem.id} ${detailSiswaItem.kehadiran}"
                            )
                            arrayListDetailSiswaItem.add(detailSiswaItem)
                        }

                        arrayListDetailSiswaItem.forEachIndexed { index, attendance ->
                            val absen = Absen(
                                attendance.kehadiran,
                                attendance.id ?: "",
                                attendance.nis
                            )
                            Log.d(
                                "TAG",
                                "refreshRecyclerViewAbsen: ATTENDANCE ${absen.siswaId} ${absen.kehadiran}"
                            )
                            absenViewModel.insertLocalAbsen(absen)
                        }
                        mtvDetailabsenAbsen.text = arrayListDetailSiswaItem.size.toString()
                        rvDetailabsenDate.apply {
                            val adapter = RvAbsenSiswaAdapter(arrayListDetailSiswaItem)
                            adapter.notifyDataSetChanged()
                            this.adapter = adapter
                            layoutManager =
                                LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                        }
                    } else {
                        requireActivity().showToast(it.message())
                    }
                }
        }
    }

    @Subscribe
    fun updateAttendanceInSiswa(commonParams: CommonParams) {
        val absen = Absen(
            commonParams.username,
            commonParams.id,
            commonParams.nis
        )
        absenViewModel.updateLocalAbsen(absen)
        Log.d("TAG", "updateAttendanceInSiswa: ${absen.siswaId} ${absen.kehadiran}")
    }

}