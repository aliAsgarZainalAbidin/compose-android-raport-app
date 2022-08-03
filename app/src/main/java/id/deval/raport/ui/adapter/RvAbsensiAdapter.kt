package id.deval.raport.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import id.deval.raport.R
import id.deval.raport.databinding.RvAbsensiBinding

class RvAbsensiAdapter(
    private val listData : ArrayList<String>,
    private val navController: NavController
) : RecyclerView.Adapter<RvAbsensiAdapter.AbsensiViewHolder>() {
    class AbsensiViewHolder(private val binding: RvAbsensiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : String, navController: NavController){
            with(binding){
                mtvRvabsensiTitlename.text = data
                clRvabsensiContainer.setOnClickListener {
                    navController.navigate(R.id.action_listAbsenFragment_to_absenDetailFragment)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsensiViewHolder {
        val view = RvAbsensiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AbsensiViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbsensiViewHolder, position: Int) {
        holder.bind(listData[position], navController)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}