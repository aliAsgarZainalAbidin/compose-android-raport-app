package id.deval.raport.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.deval.raport.databinding.RvAbsenSiswaBinding
import id.deval.raport.db.event.CommonParams
import id.deval.raport.db.response.DetailSiswaItem
import org.greenrobot.eventbus.EventBus

class RvAbsenSiswaAdapter(
    private val listSiswa: ArrayList<DetailSiswaItem>
) : RecyclerView.Adapter<RvAbsenSiswaAdapter.RvAbsenViewHolder>() {
    private val bus = EventBus.getDefault()

    class RvAbsenViewHolder(private val binding: RvAbsenSiswaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(siswa: DetailSiswaItem, bus: EventBus) {
            with(binding) {
                mtvRvitemName.text = siswa.name
                mtvRvitemNis.text = siswa.nis
                rbRvitemHadir.setOnClickListener {
                    bus.post(CommonParams(siswa.id.toString(), "Hadir", siswa.nis))
                }
                rbRvitemSakit.setOnClickListener {
                    bus.post(CommonParams(siswa.id.toString(), "Sakit", siswa.nis))
                }
                rbRvitemIzin.setOnClickListener {
                    bus.post(CommonParams(siswa.id.toString(), "Izin", siswa.nis))
                }
                rbRvitemTanpaket.setOnClickListener {
                    bus.post(CommonParams(siswa.id.toString(), "Tanpa Ket.", siswa.nis))
                }
                when (siswa.kehadiran) {
                    "Hadir" -> {
                        rbRvitemHadir.isChecked = true
                        true
                    }
                    "Sakit" -> {
                        rbRvitemSakit.isChecked = true

                        true
                    }
                    "Izin" -> {
                        rbRvitemIzin.isChecked = true

                        true
                    }
                    "Tanpa Ket." -> {
                        rbRvitemTanpaket.isChecked = true
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAbsenViewHolder {
        val view = RvAbsenSiswaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RvAbsenViewHolder(view)
    }

    override fun onBindViewHolder(holder: RvAbsenViewHolder, position: Int) {
        holder.bind(listSiswa[position], bus)
    }

    override fun getItemCount(): Int {
        return listSiswa.size
    }
}