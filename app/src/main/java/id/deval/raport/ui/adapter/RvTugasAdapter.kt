package id.deval.raport.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import id.deval.raport.R
import id.deval.raport.databinding.RvTugasBinding
import id.deval.raport.db.models.Tugas
import id.deval.raport.utils.Constanta

class RvTugasAdapter(
    private val listData : ArrayList<Tugas>,
    private val navController: NavController?
) : RecyclerView.Adapter<RvTugasAdapter.TugasViewHolder>() {
    class TugasViewHolder(private val binding: RvTugasBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Tugas, navController: NavController?){
            with(binding){
                mtvRvtugasName.text = data.nama
                mtvRvtugasNilai.text = data.nilai.toString()

                if (navController != null){
                    clRvtugasContainer.setOnClickListener {
                        val bundle = bundleOf()
                        bundle.putString(Constanta.TUGAS_ID, data.id)
                        navController.navigate(R.id.action_detailRaportFragment_to_addTugasFragment, bundle)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TugasViewHolder {
        val view = RvTugasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TugasViewHolder(view)
    }

    override fun onBindViewHolder(holder: TugasViewHolder, position: Int) {
        holder.bind(listData[position], navController)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

}