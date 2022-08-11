package id.deval.raport.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.deval.raport.databinding.RvChooseItemBinding
import id.deval.raport.db.event.CommonParams
import id.deval.raport.db.event.CommonParamsAdd
import id.deval.raport.db.event.CommonParamsDelete
import id.deval.raport.db.event.LocalDatabaseEvent
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.Siswa
import id.deval.raport.db.models.StringSiswa
import id.deval.raport.utils.hide
import id.deval.raport.utils.show
import org.greenrobot.eventbus.EventBus

class RvChooseMapelAdapter<T>(
    private val oldList: ArrayList<Mapel>
) : RecyclerView.Adapter<RvChooseMapelAdapter.RvChooseViewHolder>() {

    private var data = arrayListOf<T>()
    private var bus = EventBus.getDefault()

    fun setData(list: ArrayList<T>) {
        data.clear()
        data.addAll(list)
    }

    class RvChooseViewHolder(private val binding: RvChooseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun <T> bind(data: T, oldList: ArrayList<Mapel>, bus: EventBus) {
            with(binding) {
                mtvRvChooseItemTitlename.text = "Nama Mapel"
                mtvRvChooseItemTitlenis.text = "Kategori"
                data as Mapel
                mtvRvChooseItemName.text = data.name
                mtvRvChooseItemNis.text = data.category
                val isSame = oldList.find { it.id == data.id }
                if (isSame != null) {
                    isAlreadyAdded(true)
                } else {
                    isAlreadyAdded(false)
                }
                ivRvChooseItemEdit.setOnClickListener {
                    bus.post(LocalDatabaseEvent<Mapel>(data, "add"))
                    isAlreadyAdded(true)
                }
                ivRvChooseItemDelete.setOnClickListener {
                    bus.post(LocalDatabaseEvent<Mapel>(data, "delete"))
                    isAlreadyAdded(false)
                }
            }
        }

        fun isAlreadyAdded(boolean: Boolean){
            with(binding){
                if (boolean){
                    ivRvChooseItemEdit.hide()
                    ivRvChooseItemDelete.show()
                } else {
                    ivRvChooseItemEdit.show()
                    ivRvChooseItemDelete.hide()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvChooseViewHolder {
        val view = RvChooseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RvChooseViewHolder(view)
    }

    override fun onBindViewHolder(holder: RvChooseViewHolder, position: Int) {
        holder.bind(data[position], oldList, bus)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}