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
import id.deval.raport.databinding.FragmentAbsenOrangtuaBinding
import id.deval.raport.ui.adapter.RvAbsensiAdapter
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.DummyData

class AbsenOrangtuaFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAbsenOrangtuaBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAbsenOrangtuaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataAttendance = DummyData().setDummyAttendance()
        val mapelId = arguments?.getString(Constanta.MAPEL_ID)
        var siswaId = ""
        with(binding) {
            siswaViewModel.getSiswaByNIS(session.token.toString(), session.username.toString())
                .observe(viewLifecycleOwner) {
                    mtvAbsenorangtuaName.text = it.data.name
                    mtvAbsenorangtuaNis.text = it.data.nis
                    siswaId = it.data.id

                    Log.d(TAG, "onViewCreated: $mapelId $siswaId")

                    if (!mapelId.isNullOrEmpty()) {
                        absenViewModel.getAttendanceBySiswaId(session.token.toString(), siswaId, mapelId)
                            .observe(viewLifecycleOwner) {

                                Log.d(TAG, "onViewCreated: ${it.data}")

                                rvAbsenorangtuaDate.apply {
                                    val adapter = RvAbsensiAdapter(it.data, mainNavController, siswaId)
                                    adapter.notifyDataSetChanged()
                                    this.adapter = adapter
                                    layoutManager =
                                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                                }
                            }
                    }
                }
        }
    }

}