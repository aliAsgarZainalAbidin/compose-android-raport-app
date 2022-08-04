package id.deval.raport.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.deval.raport.databinding.RvGrowthBinding
import id.deval.raport.db.models.Growth

class RvGrowthAdapter(
    private val listData : ArrayList<Growth>
) : RecyclerView.Adapter<RvGrowthAdapter.ViewHolder>() {
    class ViewHolder(private val binding : RvGrowthBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Growth){
            with(binding){
                mtvRvgrowthTanggal.text = data.tanggal
                mtvRvgrowthTinggi.text = data.tinggi.toString()
                mtvRvgrowthBerat.text = data.berat.toString()
                mtvRvgrowthBmi.text = data.bmi.toString()
                mtvRvgrowthKet.text = data.ket
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvGrowthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

}