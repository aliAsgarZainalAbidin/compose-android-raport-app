package id.deval.raport.ui.growth

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddGrowthBinding
import id.deval.raport.db.models.Growth
import id.deval.raport.db.models.request.GrowthAdd
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.hideError
import id.deval.raport.utils.showToast
import java.text.SimpleDateFormat
import java.util.*

class AddGrowthFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentAddGrowthBinding
    private val binding get() = _binding
    private var isValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddGrowthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pesanId = arguments?.getString(Constanta.ID)
        with(binding){
            ivAddgrowthBack.setOnClickListener {
                mainNavController.popBackStack()
            }

            Log.d(TAG, "onViewCreated: PESAN ID $pesanId")
            tietAddgrowthTinggi.hideError()
            tietAddgrowthBerat.hideError()
            tietAddgrowthBmi.hideError()
            tietAddgrowthKet.hideError()

            mbAddgrowthSimpan.setOnClickListener {
                val tinggi = tietAddgrowthTinggi.text.toString()
                val berat = tietAddgrowthBerat.text.toString()
                val bmi = tietAddgrowthBmi.text.toString()
                val ket = tietAddgrowthKet.text.toString()

                if (tinggi.isEmpty()){
                    isValid = false
                    tietAddgrowthTinggi.error = resources.getString(R.string.tiet_empty)
                }

                if (berat.isEmpty()){
                    isValid = false
                    tietAddgrowthBerat.error = resources.getString(R.string.tiet_empty)
                }

                if(bmi.isEmpty()){
                    isValid = false
                    tietAddgrowthBmi.error = resources.getString(R.string.tiet_empty)
                }

                if (tinggi.isNotEmpty() && berat.isNotEmpty() && bmi.isNotEmpty()){
                    isValid = true
                }

                if (isValid){
                    val formater = SimpleDateFormat("dd/M/yyyy")
                    val date = formater.format(Date())

                    val growth = GrowthAdd(
                        berat.toInt(), date, ket, tinggi.toInt(), bmi.toInt(), pesanId
                    )
                    pesanViewModel.addGrowth(session.token.toString(), growth).observe(viewLifecycleOwner){
                        if (it.isSuccessful){
                            requireContext().showToast("${it.body()?.status} menambahkan data")
                            mainNavController.popBackStack()
                        } else {
                            requireContext().showToast(it.message())
                        }
                    }
                }
            }
        }
    }

}