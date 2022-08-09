package id.deval.raport.ui.akun.siswa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddSiswaBinding
import id.deval.raport.db.models.Siswa
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.hide
import id.deval.raport.utils.showToast

class AddSiswaFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAddSiswaBinding
    private val binding get() = _binding
    private lateinit var id: String
    private var isValid = false

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
        with(binding) {
            ivAddsiswaBack.setOnClickListener {
                mainNavController.popBackStack()
            }

            if (id.isNotEmpty()) {
                TODO()
            }

            when (role) {
                "orangtua" -> viewAsOrangtua()
                "admin" -> viewAsAdmin()
            }
        }
    }

    fun viewAsAdmin() {
        with(binding) {
            Log.d("TAG", "viewAsAdmin: ADMIN")
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

                if (namaLengkap.isEmpty()) {
                    tilAddsiswaNamalengkap.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (nisn.isEmpty()) {
                    tietAddsiswaNisn.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (tempatLahir.isEmpty()) {
                    tietAddsiswaTempatlahir.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (tanggallahir.isEmpty()) {
                    tietAddsiswaTanggalLahir.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (gender.isEmpty()) {
                    tietAddsiswaGender.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (religion.isEmpty()) {
                    tietAddsiswaReligion.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (eduction.isEmpty()) {
                    tietAddsiswaEducation.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (address.isEmpty()) {
                    tietAddsiswaAddress.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (namaAyah.isEmpty()) {
                    tietAddsiswaNamaAyah.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (namaIbu.isEmpty()) {
                    tietAddsiswaNamaIbu.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (pekerjaanAyah.isEmpty()) {
                    tietAddsiswaPekerjaanAyah.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (pekerjaanIbu.isEmpty()) {
                    tietAddsiswaPekerjaanIbu.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (namaWali.isEmpty()) {
                    tietAddsiswaNamaWali.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (pekerjaanWali.isEmpty()) {
                    tietAddsiswaPekerjaanWali.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (alamatWali.isEmpty()) {
                    tietAddsiswaAlamatWali.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (hp.isEmpty()) {
                    tietAddsiswaHp.error = resources.getString(R.string.tiet_empty)
                    isValid = false
                }

                if (namaLengkap.isNotEmpty() && nisn.isNotEmpty() && tempatLahir.isNotEmpty() && tanggallahir.isNotEmpty() && gender.isNotEmpty() && religion.isNotEmpty() && eduction.isNotEmpty() && address.isNotEmpty() && namaAyah.isNotEmpty() && namaIbu.isNotEmpty() && pekerjaanAyah.isNotEmpty() && pekerjaanIbu.isNotEmpty() && namaWali.isNotEmpty() && pekerjaanWali.isNotEmpty() && alamatWali.isNotEmpty() && hp.isNotEmpty()) {
                    isValid = true
                }


                if (isValid) {
                    val siswa = Siswa(
                        null,
                        address,
                        eduction,
                        gender,
                        pekerjaanWali,
                        tanggallahir,
                        alamatWali,
                        religion,
                        namaAyah,
                        null,
                        namaWali,
                        namaIbu,
                        hp,
                        namaLengkap,
                        nisn,
                        tempatLahir,
                        pekerjaanAyah,
                        pekerjaanIbu
                    )

                    val bundle = bundleOf()
                    bundle.putParcelable(Constanta.PARCELABLE_ITEM, siswa)
                    mainNavController.navigate(R.id.action_addSiswaFragment_to_addOrangTuaFragment, bundle)
                }
            }
        }
    }

    fun viewAsOrangtua() {
        with(binding) {
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