package id.deval.raport.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import id.deval.raport.R
import id.deval.raport.databinding.RvAbsensiBinding
import id.deval.raport.db.models.Attendance
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.show

class RvAbsensiAdapter(
    private val listData : ArrayList<Attendance>,
    private val navController: NavController,
    private val siswaId: String?
) : RecyclerView.Adapter<RvAbsensiAdapter.AbsensiViewHolder>() {
    class AbsensiViewHolder(private val binding: RvAbsensiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Attendance, navController: NavController, siswaId: String?){
            with(binding){
                val context = binding.root.context
                mtvRvabsensiTitlename.text = data.tanggalAbsen
                if (siswaId != null){
                    data.attendance?.forEach {
                        if (siswaId == it?.siswaId){
                            mtvRvabsensiKehadiran.text = it.kehadiran
                            when (it.kehadiran){
                                "Hadir" -> mtvRvabsensiKehadiran.setTextColor(context.resources.getColor(R.color.green))
                                "Sakit" -> mtvRvabsensiKehadiran.setTextColor(context.resources.getColor(R.color.warning))
                                "Izin" -> mtvRvabsensiKehadiran.setTextColor(context.resources.getColor(R.color.blue))
                                "Tanpa Ket." -> mtvRvabsensiKehadiran.setTextColor(context.resources.getColor(R.color.red))
                            }
                            mtvRvabsensiKehadiran.show()
                        }
                    }
                }
                if (siswaId == null){
                    clRvabsensiContainer.setOnClickListener {
                        val bundle = bundleOf()
                        bundle.putString(Constanta.DATE,data.tanggalAbsen)
                        bundle.putString(Constanta.ID, data.id)
                        navController.navigate(R.id.action_listAbsenFragment_to_absenDetailFragment, bundle)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsensiViewHolder {
        val view = RvAbsensiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AbsensiViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbsensiViewHolder, position: Int) {
        holder.bind(listData[position], navController, siswaId)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}