package id.deval.raport.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.deval.raport.databinding.RvGrowthBinding
import id.deval.raport.databinding.RvNoteBinding
import id.deval.raport.db.models.Growth
import id.deval.raport.db.models.Note

class RvNoteAdapter(
    private val listData: ArrayList<Note>
) : RecyclerView.Adapter<RvNoteAdapter.ViewHolder>() {
    class ViewHolder(private val binding: RvNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Note) {
            with(binding) {
                mtvRvgrowthTanggal.text = data.tanggal
                mtvRvgrowthKet.text = data.keterangan
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

}