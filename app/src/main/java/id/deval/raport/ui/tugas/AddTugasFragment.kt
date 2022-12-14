package id.deval.raport.ui.tugas

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddTugasBinding
import id.deval.raport.db.models.Tugas
import id.deval.raport.db.models.request.TugasAdd
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.hideError
import id.deval.raport.utils.showToast

class AddTugasFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAddTugasBinding
    private val binding get() = _binding
    private var isValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTugasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val raportId = arguments?.getString(Constanta.ID)
        val tugasId = arguments?.getString(Constanta.TUGAS_ID)
        with(binding) {
            ivAddtugasBack.setOnClickListener {
                try {
                    mainNavController.popBackStack()
                }catch (e: Exception){
                    Log.d(TAG, "onViewCreated: $e")
                }
            }

            if (!tugasId.isNullOrEmpty()) {
                raportViewModel.getTugasById(session.token.toString(), tugasId)
                    .observe(viewLifecycleOwner) {
                        if (it.isSuccessful){
                            tietAddtugasNama.setText(it.body()?.data!!.nama)
                            tietAddtugasNilaitugas.setText(it.body()?.data!!.nilai.toString())
                        } else {
                            requireContext().showToast(it.message())
                        }
                    }
            }
            tietAddtugasNama.hideError()
            tietAddtugasNilaitugas.hideError()

            mbAddtugasSimpan.setOnClickListener {
                val nama = tietAddtugasNama.text.toString()
                val nilai = tietAddtugasNilaitugas.text.toString()

                if (nama.isEmpty()) {
                    isValid = false
                    tietAddtugasNama.error = resources.getString(R.string.tiet_empty)
                }
                if (nilai.isEmpty()) {
                    isValid = false
                    tietAddtugasNilaitugas.error = resources.getString(R.string.tiet_empty)
                }

                if (nilai.isNotEmpty() && nama.isNotEmpty()) {
                    isValid = true
                }

                if (isValid) {
                    val tugas = TugasAdd(nama, nilai.toInt(), raportId)
                    if (!tugasId.isNullOrEmpty()) {
                        raportViewModel.updateTugasById(session.token.toString(), tugasId, tugas)
                            .observe(viewLifecycleOwner) {
                                if (it.isSuccessful){
                                    requireContext().showToast("${it.body()?.status} men-update tugas")
                                } else {
                                    requireContext().showToast(it.message())
                                }
                            }
                    } else {
                        raportViewModel.addTugas(session.token.toString(), tugas)
                            .observe(viewLifecycleOwner) {
                                if (it.isSuccessful){
                                    try {
                                        requireContext().showToast("${it.body()?.status} menambahkan tugas")
                                        mainNavController.popBackStack()
                                    }catch (e: Exception){
                                        Log.d(TAG, "onViewCreated: $e")
                                    }
                                } else {
                                    requireContext().showToast(it.message())
                                }
                            }
                    }
                }
            }
        }
    }

}