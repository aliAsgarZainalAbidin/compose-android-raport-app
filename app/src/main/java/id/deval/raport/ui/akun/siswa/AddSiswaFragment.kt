package id.deval.raport.ui.akun.siswa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddSiswaBinding
import id.deval.raport.db.models.Siswa
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.hide

class AddSiswaFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentAddSiswaBinding
    private val binding get() =  _binding
    private lateinit var id : String

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
        id = arguments?.getString(Constanta.ID) ?: ""
        with(binding){
            ivAddsiswaBack.setOnClickListener {
                mainNavController.popBackStack()
            }

            if (id.isNotEmpty()){
                TODO()
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
                val namaLengkap = tietAddsiswaNamalengkap.text.toString()
                val nisn = tietAddsiswaNisn.text.toString()
                val tempatLahir = tietAddsiswaTempatlahir.text.toString()
                val tanggallahir = tietAddsiswaTanggalLahir.text.toString()
                val gender = tietAddsiswaGender.text.toString()
                val religion = tietAddsiswaReligion.text.toString()
                val eduction = tietAddsiswaEducation.text.toString()
                val address = tietAddsiswaAddress.text.toString()
                val namaAyah = tietAddsiswaNamaAyah.text.toString()
                val namaIbu = tietAddsiswaNamaIbu.text.toString()
                val pekerjaanAyah = tietAddsiswaPekerjaanAyah.text.toString()
                val pekerjaanIbu = tietAddsiswaPekerjaanIbu.text.toString()
                val namaWali = tietAddsiswaNamaWali.text.toString()
                val pekerjaanWali = tietAddsiswaPekerjaanWali.text.toString()
                val alamatWali = tietAddsiswaAlamatWali.text.toString()
                val hp = tietAddsiswaHp.text.toString()

                val siswa = Siswa(
                    null,
                )

                siswaViewModel.addSiswa(session.token.toString(), siswa).observe(viewLifecycleOwner){

                }
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