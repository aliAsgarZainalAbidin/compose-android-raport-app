package id.deval.raport.ui.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import id.deval.raport.R
import id.deval.raport.databinding.RvItemBinding
import id.deval.raport.db.event.CommonParams
import id.deval.raport.db.event.EventToDetailRaport
import id.deval.raport.db.models.Account
import id.deval.raport.db.models.Kelas
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.OperationsTypeRv
import id.deval.raport.utils.show
import org.greenrobot.eventbus.EventBus

class RvAdapter<T>(
    private val typeAdapter: String,
    private val typeOperation: OperationsTypeRv,
    private val navController: NavController,
    private var maxItemShow : Int? = null,
) : RecyclerView.Adapter<RvAdapter.RvViewHolder>() {

    private var listData = arrayListOf<T>()
    private var bus = EventBus.getDefault()

    fun setData(list: ArrayList<T>) {
        listData.clear()
        listData.addAll(list)
    }

    class RvViewHolder(private var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun <T> bind(type: String, data: T, operationsTypeRv: OperationsTypeRv,navController: NavController, maxItemShow: Int?, bus: EventBus) {
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
                        ivRvitemEdit.setOnClickListener {
                            try {
                                val bundle = bundleOf()
                                bundle.putString(Constanta.ID, data.id)
                                navController.navigate(R.id.action_registrasiGuruFragment_to_addGuruFragment,bundle)
                            }catch (e: Exception){
                                Log.d(ContentValues.TAG, "onViewCreated: $e")
                            }
                        }

                        ivRvitemDelete.setOnClickListener {
                            Log.d("TAG", "bind: ONCLICK DELETE ITEM")
                            bus.post(CommonParams(data.id.toString()))
                        }
                        true
                    }
                    "siswa" -> {
                        bindingSiswaRv(data as Siswa)
                        ivRvitemEdit.setOnClickListener {
                            try {
                                val bundle = bundleOf()
                                bundle.putString(Constanta.ID, data.id)
                                bundle.putString(Constanta.ROLE, "admin")
                                navController.navigate(R.id.action_registrasiSiswaFragment_to_addSiswaFragment, bundle)
                            }catch (e: Exception){
                                Log.d(ContentValues.TAG, "onViewCreated: $e")
                            }
                        }

                        ivRvitemDelete.setOnClickListener {
                            Log.d("TAG", "bind: ONCLICK DELETE ITEM")
                            bus.post(CommonParams(data.id.toString(), data.nis))
                        }
                        true
                    }
                    "siswa-raport" -> {
                        bindingSiswaRv(data as Siswa)
                        clIvitemContainer.setOnClickListener {
                            bus.post(CommonParams(data.id))
                        }
                        true
                    }
                    "siswa-pesan" -> {
                        bindingSiswaRv(data as Siswa)
                        clIvitemContainer.setOnClickListener {
                            try {
                                val bundle = bundleOf()
                                bundle.putString(Constanta.ID, data.id)
                                navController.navigate(R.id.action_baseFragment_to_addPesanFragment, bundle)
                            }catch (e: Exception){
                                Log.d(ContentValues.TAG, "onViewCreated: $e")
                            }
                        }
                        true
                    }
                    "kelas" -> {
                        data as Kelas
                        mtvRvitemTitlename.text = "Nama Kelas"
                        mtvRvitemTitlenis.text = "Jumlah Siswa"
                        mtvRvitemName.text = data.name
                        mtvRvitemNis.text = data.siswaId?.size.toString()

                        ivRvitemEdit.setOnClickListener {
                            try {
                                val bundle = bundleOf()
                                bundle.putString(Constanta.ID, data.id)
                                navController.navigate(R.id.action_baseFragment_to_addKelasFragment, bundle)
                            }catch (e: Exception){
                                Log.d(ContentValues.TAG, "onViewCreated: $e")
                            }
                        }

                        ivRvitemDelete.setOnClickListener {
                            bus.post(CommonParams(data.id.toString()))
                        }

                        true
                    }
                    "mapel-absen" -> {
                        bindingMapelRv(data as Mapel)
                        clIvitemContainer.setOnClickListener {
                            bus.post(CommonParams(data.id,data.name))
                        }
                        true
                    }
                    "mapel-absen-orangtua" -> {
                        bindingMapelRv(data as Mapel)
                        clIvitemContainer.setOnClickListener {
                            try {
                                val bundle = bundleOf()
                                bundle.putString(Constanta.MAPEL_ID, data.id)
                                bundle.putString(Constanta.MAPEL_NAME, data.name)
                                navController.navigate(R.id.action_baseFragment_to_absenOrangtuaFragment, bundle)
                            }catch (e: Exception){
                                Log.d(ContentValues.TAG, "onViewCreated: $e")
                            }
                        }
                        true
                    }
                    "mapel" -> {
                        bindingMapelRv(data as Mapel)
                        ivRvitemEdit.setOnClickListener {
                            try {
                                val bundle = bundleOf()
                                bundle.putString(Constanta.ID, data.id)
                                navController.navigate(R.id.action_baseFragment_to_addMapelFragment, bundle)
                            }catch (e: Exception){
                                Log.d(ContentValues.TAG, "onViewCreated: $e")
                            }
                        }

                        ivRvitemDelete.setOnClickListener {
                            bus.post(CommonParams(data.id))
                        }
                        true
                    }
                    "mapel-raport" -> {
                        bindingMapelRv(data as Mapel)
                        clIvitemContainer.setOnClickListener {
                            bus.post(CommonParams(data.id,data.name))
                        }
                        true
                    }
                    "mapel-raport-orangtua" -> {
                        bindingMapelRv(data as Mapel)
                        clIvitemContainer.setOnClickListener {
                            bus.post(EventToDetailRaport(data.id, data.name))
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
        holder.bind(typeAdapter, listData[position], typeOperation,navController, maxItemShow, bus)
    }

    override fun getItemCount(): Int {
        if (listData.size < 2){
            maxItemShow = listData.size
        }
        return maxItemShow ?: listData.size
    }
}