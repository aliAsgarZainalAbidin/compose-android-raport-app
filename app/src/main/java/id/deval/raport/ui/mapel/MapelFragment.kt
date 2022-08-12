package id.deval.raport.ui.mapel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentMapelBinding
import id.deval.raport.db.event.CommonParams
import id.deval.raport.db.models.Mapel
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*
import org.greenrobot.eventbus.Subscribe

class MapelFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentMapelBinding
    private val binding get() = _binding
    lateinit var dataMapel : ArrayList<Mapel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataMapel = arrayListOf()
        with(binding) {
            mtvMapelName.text = session.name
            includeRvMapel.mtvRvlayoutAdd.show()
            includeRvMapel.mtvRvlayoutAdd.text = "Tambah Mapel"
            includeRvMapel.mtvRvlayoutAdd.setOnClickListener {
                mainNavController.navigate(R.id.action_baseFragment_to_addMapelFragment)
            }
            includeRvMapel.mtvRvlayoutTitle.text = "Mata Pelajaran"
            includeRvMapel.mtvRvlayoutViewmore.hide()

            refreshRecyclerView()
        }
    }

    fun refreshRecyclerView(){
        with(binding){
            mapelViewModel.getAllMapel(session.token.toString()).observe(viewLifecycleOwner) {
                includeRvMapel.rvRvlayoutContainer.apply {
                    val adapter =
                        RvAdapter<Mapel>("mapel", OperationsTypeRv.EDIT, mainNavController)
//                    dataMapel.addAll(it.data)
                    mtvMapelTotalmapel.text = it.data.size.toString()

                    adapter.setData(it.data)
                    adapter.notifyDataSetChanged()
                    this.adapter = adapter
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

    @Subscribe
    override fun deleteItem(commonParams: CommonParams) {
        super.deleteItem(commonParams)
        mapelViewModel.deleteMapelById(session.token.toString(), commonParams.id).observe(viewLifecycleOwner){
            requireContext().showToast("Berhasil menghapus data")
            refreshRecyclerView()
        }
    }
}