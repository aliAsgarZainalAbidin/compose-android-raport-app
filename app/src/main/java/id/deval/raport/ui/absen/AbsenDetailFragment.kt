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
    lateinit var updateAttendance: AttendanceAdd

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAbsenDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataKehadiran = DummyData().setDummyDataAbsenSiswa()
        arrayListDetailSiswaItem = arrayListOf()
        val date = arguments?.getString(Constanta.DATE)
        val id = arguments?.getString(Constanta.ID)
        updateAttendance = AttendanceAdd()
        with(binding) {
            mtvDetailabsenDate.text = date

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
                        Log.d(TAG, "onViewCreated: $listAttendanceItem")
                        updateAttendanceSiswa(id,listAttendanceItem)
                    }
                }
            }
        }
    }

    fun updateAttendanceSiswa(id: String, data : ArrayList<AttendanceItem>) {
        updateAttendance.attendance = data
        absenViewModel.updateAttendanceById(session.token.toString(), id, updateAttendance)
            .observe(viewLifecycleOwner) {
                if (it.isSuccessful){
                    requireContext().showToast("${it.body()?.status} men-update data")
                } else {
                    requireActivity().showToast(it.message())
                }
            }
    }

    fun refreshRecyclerViewAbsen(id: String) {
        with(binding) {
            absenViewModel.getAttendanceById(session.token.toString(), id)
                .observe(viewLifecycleOwner) {
                    if (it.isSuccessful){
                        updateAttendance.classId = it.body()!!.data[0].classId
                        updateAttendance.mapelId = it.body()!!.data[0].mapelId
                        updateAttendance.tanggalAbsen = it.body()!!.data[0].tanggalAbsen

                        it.body()!!.data[0].detailSiswa?.forEachIndexed { i, siswa ->
                            val detailSiswaItem = DetailSiswaItem(
                                siswa?.name,
                                siswa?.nis,
                                it.body()!!.data[0].attendance?.get(i)?.kehadiran,
                                siswa?.id
                            )
                            Log.d(TAG, "refreshRecyclerViewAbsen: DETAIL ${detailSiswaItem.id} ${detailSiswaItem.kehadiran}")
                            arrayListDetailSiswaItem.add(detailSiswaItem)
                        }
                        it.body()!!.data[0].attendance?.forEachIndexed { index, attendance ->
                            val absen = Absen(
                                attendance?.kehadiran,
                                attendance?.siswaId ?: "",
                                it.body()!!.data[0].detailSiswa?.get(index)?.nis
                            )
                            Log.d(TAG, "refreshRecyclerViewAbsen: ATTENDANCE ${absen.siswaId} ${absen.kehadiran}")
                            absenViewModel.insertLocalAbsen(absen)
                        }
                        Log.d("TAG", "onViewCreated: $arrayListDetailSiswaItem")

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
        Log.d(TAG, "updateAttendanceInSiswa: ${absen.siswaId} ${absen.kehadiran}")
    }

}