package id.deval.raport.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.deval.raport.databinding.RvItemBinding
import id.deval.raport.db.models.Account
import id.deval.raport.db.models.Kelas
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.utils.OperationsTypeRv

class RvAdapter<T>(
    private val typeAdapter: String,
    private val typeOperation: OperationsTypeRv,
    private val maxItemShow : Int? = null
) : RecyclerView.Adapter<RvAdapter.RvViewHolder>() {

    private var listData = arrayListOf<T>()

    fun setData(list: ArrayList<T>) {
        listData.clear()
        listData.addAll(list)
    }

    class RvViewHolder(private var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun <T> bind(type: String, data: T, operationsTypeRv: OperationsTypeRv) {
            with(binding) {
                when (type) {
                    "guru" -> {
                        data as Account
                        mtvRvitemTitlename.text = "Nama"
                        mtvRvitemTitlenis.text = "NIK"
                        mtvRvitemName.text = data.name
                        mtvRvitemNis.text = data.username
                        true
                    }
                    "siswa" -> {
                        data as Siswa
                        mtvRvitemTitlename.text = "Nama"
                        mtvRvitemTitlenis.text = "NIS"
                        mtvRvitemName.text = data.name
                        mtvRvitemNis.text = data.nis
                        true
                    }
                    "kelas" -> {
                        data as Kelas
                        mtvRvitemTitlename.text = "Nama Kelas"
                        mtvRvitemTitlenis.text = "Jumlah Siswa"
                        mtvRvitemName.text = data.name
                        mtvRvitemNis.text = data.siswaId?.size.toString()
                        true
                    }
                    "mapel" -> {
                        data as Mapel
                        mtvRvitemTitlename.text = "Nama Mapel"
                        mtvRvitemTitlenis.text = "Kategori"
                        mtvRvitemName.text = data.name
                        mtvRvitemNis.text = data.category
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.bind(typeAdapter, listData[position], typeOperation)
    }

    override fun getItemCount(): Int {
        return maxItemShow ?: listData.size
    }
}