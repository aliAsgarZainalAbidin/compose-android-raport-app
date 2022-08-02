package id.deval.raport.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.deval.raport.databinding.RvChooseItemBinding
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa

class RvChooseAdapter<T>(
    private val typeAdapter: String
) : RecyclerView.Adapter<RvChooseAdapter.RvChooseViewHolder>(){

    private var data = arrayListOf<T>()

    fun setData(list : ArrayList<T>){
        data.clear()
        data.addAll(list)
    }

    class RvChooseViewHolder(private val binding : RvChooseItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun <T> bind(typeAdapter: String, data: T){
            with(binding){
                when(typeAdapter) {
                    "siswa" -> {
                        mtvRvChooseItemTitlename.text = "Nama"
                        mtvRvChooseItemTitlenis.text = "NIS"
                        data as Siswa
                        mtvRvChooseItemName.text = data.name
                        mtvRvChooseItemNis.text = data.nis
                        true
                    }
                    "mapel" -> {
                        mtvRvChooseItemTitlename.text = "Nama Mapel"
                        mtvRvChooseItemTitlenis.text = "Kategori"
                        data as Mapel
                        mtvRvChooseItemName.text = data.name
                        mtvRvChooseItemNis.text = data.category
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvChooseViewHolder {
        val view = RvChooseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RvChooseViewHolder(view)
    }

    override fun onBindViewHolder(holder: RvChooseViewHolder, position: Int) {
        holder.bind(typeAdapter,data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}