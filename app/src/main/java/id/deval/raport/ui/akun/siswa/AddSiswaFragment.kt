package id.deval.raport.ui.akun.siswa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddSiswaBinding
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.hide

class AddSiswaFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentAddSiswaBinding
    private val binding get() =  _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSiswaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val role = arguments?.getString(Constanta.ROLE).toString()
        with(binding){
            ivAddsiswaBack.setOnClickListener {
                mainNavController.popBackStack()
            }

            when(role){
                "orangtua" -> viewAsOrangtua()
                "admin" -> viewAsAdmin()
            }
        }
    }

    fun viewAsAdmin(){
        with(binding){
            mbAddsiswaSimpan.setOnClickListener {
                mainNavController.navigate(R.id.action_addSiswaFragment_to_addOrangTuaFragment)
            }
        }
    }

    fun viewAsOrangtua(){
        with(binding){
            mtvAddsiswaName.text = "Profile Siswa"

            mbAddsiswaSimpan.hide()
            tilAddsiswaAddress.isEnabled = false
            tilAddsiswaAlamatWali.isEnabled = false
            tilAddsiswaEducation.isEnabled = false
            tilAddsiswaGender.isEnabled = false
            tilAddsiswaHp.isEnabled = false
            tilAddsiswaKelas.isEnabled = false
            tilAddsiswaNamaAyah.isEnabled = false
            tilAddsiswaNamaIbu.isEnabled = false
            tilAddsiswaNamaWali.isEnabled = false
            tilAddsiswaNamalengkap.isEnabled = false
            tilAddsiswaNisn.isEnabled = false
            tilAddsiswaPekerjaanAyah.isEnabled = false
            tilAddsiswaPekerjaanIbu.isEnabled = false
            tilAddsiswaPekerjaanWali.isEnabled = false
            tilAddsiswaReligion.isEnabled = false
            tilAddsiswaTanggalLahir.isEnabled = false
            tilAddsiswaTempatlahir.isEnabled = false
            ivAddsiswaPhoto.isEnabled = false
        }
    }

}