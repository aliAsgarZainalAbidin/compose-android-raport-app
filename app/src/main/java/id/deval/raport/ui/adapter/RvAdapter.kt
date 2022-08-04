package id.deval.raport.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import id.deval.raport.R
import id.deval.raport.databinding.RvItemBinding
import id.deval.raport.db.models.Account
import id.deval.raport.db.models.Kelas
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.utils.OperationsTypeRv
import id.deval.raport.utils.show

class RvAdapter<T>(
    private val typeAdapter: String,
    private val typeOperation: OperationsTypeRv,
    private val navController: NavController,
    private val maxItemShow : Int? = null,
) : RecyclerView.Adapter<RvAdapter.RvViewHolder>() {

    private var listData = arrayListOf<T>()

    fun setData(list: ArrayList<T>) {
        listData.clear()
        listData.addAll(list)
    }

    class RvViewHolder(private var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun <T> bind(type: String, data: T, operationsTypeRv: OperationsTypeRv,navController: NavController, maxItemShow: Int?) {
            with(binding) {
                if (operationsTypeRv == OperationsTypeRv.EDIT){
                    ivRvitemDelete.show()
                    ivRvitemEdit.show()
                }

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
                        bindingSiswaRv(data as Siswa)
                        true
                    }
                    "siswa-raport" -> {
                        bindingSiswaRv(data as Siswa)
                        clIvitemContainer.setOnClickListener {
                            navController.navigate(R.id.action_listRaportFragment_to_detailRaportFragment)
                        }
                        true
                    }
                    "siswa-pesan" -> {
                        bindingSiswaRv(data as Siswa)
                        clIvitemContainer.setOnClickListener {
                            navController.navigate(R.id.action_baseFragment_to_addPesanFragment)
                        }
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
                    "mapel-absen" -> {
                        bindingMapelRv(data as Mapel)
                        clIvitemContainer.setOnClickListener {
                            navController.navigate(R.id.action_baseFragment_to_listAbsenFragment)
                        }
                        true
                    }
                    "mapel" -> {
                        bindingMapelRv(data as Mapel)
                        true
                    }
                    "mapel-raport" -> {
                        bindingMapelRv(data as Mapel)
                        clIvitemContainer.setOnClickListener {
                            navController.navigate(R.id.action_baseFragment_to_listRaportFragment)
                        }
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }

        fun bindingMapelRv(data : Mapel){
            with(binding){
                mtvRvitemTitlename.text = "Nama Mapel"
                mtvRvitemTitlenis.text = "Kategori"
                mtvRvitemName.text = data.name
                mtvRvitemNis.text = data.category
            }
        }

        fun bindingSiswaRv(data : Siswa){
            with(binding){
                mtvRvitemTitlename.text = "Nama"
                mtvRvitemTitlenis.text = "NIS"
                mtvRvitemName.text = data.name
                mtvRvitemNis.text = data.nis
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.bind(typeAdapter, listData[position], typeOperation,navController, maxItemShow)
    }

    override fun getItemCount(): Int {
        return maxItemShow ?: listData.size
    }
}