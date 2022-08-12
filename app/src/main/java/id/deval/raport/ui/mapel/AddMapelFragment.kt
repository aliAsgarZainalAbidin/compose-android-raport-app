package id.deval.raport.ui.mapel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddMapelBinding
import id.deval.raport.db.models.Mapel
import id.deval.raport.db.models.request.MapelAdd
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.showToast

class AddMapelFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAddMapelBinding
    private val binding get() = _binding
    private var isValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddMapelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString(Constanta.ID, "")
        with(binding) {
            ivAddmapelBack.setOnClickListener {
                mainNavController.popBackStack()
            }

            Log.d("TAG", "onViewCreated: $id")
            if (!id.isNullOrEmpty()){
                mapelViewModel.getMapelById(session.token.toString(), id).observe(viewLifecycleOwner){
                    val data = it.data
                    tietAddmapelNama.setText(data.name)
                    tietAddmapelGuru.setText(data.category, false)
                }
            }

            val adapterKategori = ArrayAdapter(
                requireContext(),
                R.layout.list_item,
                resources.getStringArray(R.array.kategori)
            )
            tietAddmapelGuru.setAdapter(adapterKategori)
            mbAddmapelSimpan.setOnClickListener {
                val nama = tietAddmapelNama.text.toString()
                val kategori = tietAddmapelGuru.text.toString()

                if (nama.isEmpty()) {
                    tietAddmapelNama.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (kategori.isEmpty()) {
                    tietAddmapelGuru.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (nama.isNotEmpty() && kategori.isNotEmpty()) {
                    isValid = true
                }

                if (isValid) {
                    val mapel = MapelAdd(nama, kategori)

                    mapelViewModel.addMapel(session.token.toString(), mapel)
                        .observe(viewLifecycleOwner) {
                            requireContext().showToast("${it.status} menambahkan data")
                            mainNavController.popBackStack()
                        }
                }

            }
        }
    }

}