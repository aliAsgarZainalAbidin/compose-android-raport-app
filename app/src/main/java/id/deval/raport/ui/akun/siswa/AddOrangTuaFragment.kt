package id.deval.raport.ui.akun.siswa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddOrangTuaBinding
import id.deval.raport.db.models.Account
import id.deval.raport.db.models.Siswa
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.showToast
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class AddOrangTuaFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAddOrangTuaBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrangTuaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val siswa: Siswa? = arguments?.getParcelable(Constanta.PARCELABLE_ITEM)
        val path = arguments?.getString(Constanta.PATH_IMAGE)
        var alamat = ""

        Log.d("TAG", "onViewCreated: $siswa")
        with(binding) {
            if (siswa != null) {
                tietAddorangtuaNisn.setText(siswa.nis)
                alamat =
                    if (siswa.address != null && siswa.address != "-") siswa.address else siswa.alamatWali.toString()
                tietAddorangtuaAlamat.setText(alamat)
                tietAddorangtuaHp.setText(siswa.phone)
            }

            ivAddorangtuaBack.setOnClickListener {
                mainNavController.popBackStack()
            }
            mbAddorangtuaSimpan.setOnClickListener {
                val namaLengkap = tietAddorangtuaNamalengkap.text.toString()
                val password = tietAddorangtuaPassword.text.toString()
                val nomorHp = tietAddorangtuaHp.text.toString()
                val email = tietAddorangtuaEmail.text.toString()
                val nik = tietAddorangtuaNisn.text.toString()

                val account = Account(
                    null,
                    password,
                    "OrangTua",
                    alamat,
                    password,
                    nomorHp,
                    namaLengkap,
                    email,
                    nik,
                    null
                )
                val imageBitmap = File(path)
                val requestImageBody = imageBitmap.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val photo = MultipartBody.Part.createFormData(
                    "photo",
                    imageBitmap.name,
                    requestImageBody
                )
                val name = MultipartBody.Part.createFormData("name", siswa!!.name.toString())
                val nis = MultipartBody.Part.createFormData("nis", siswa.nis.toString())
                val tempatLahir =
                    MultipartBody.Part.createFormData("tempatLahir", siswa.tempatLahir.toString())
                val tanggalLahir =
                    MultipartBody.Part.createFormData("tanggalLahir", siswa.tanggalLahir.toString())
                val address = MultipartBody.Part.createFormData("address", siswa.address.toString())
                val education =
                    MultipartBody.Part.createFormData("education", siswa.education.toString())
                val religion =
                    MultipartBody.Part.createFormData("religion", siswa.religion.toString())
                val gender = MultipartBody.Part.createFormData("gender", siswa.gender.toString())
                val namaAyah =
                    MultipartBody.Part.createFormData("namaAyah", siswa.namaAyah.toString())
                val namaIbu = MultipartBody.Part.createFormData("namaIbu", siswa.namaIbu.toString())
                val pekerjaanAyah = MultipartBody.Part.createFormData(
                    "pekerjaanAyah",
                    siswa.pekerjaanAyah.toString()
                )
                val pekerjaanIbu =
                    MultipartBody.Part.createFormData("pekerjaanIbu", siswa.pekerjaanIbu.toString())
                val namaWali =
                    MultipartBody.Part.createFormData("namaWali", siswa.namaWali.toString())
                val pekerjaanWali = MultipartBody.Part.createFormData(
                    "pekerjaanWali",
                    siswa.pekerjaanWali.toString()
                )
                val alamatWali =
                    MultipartBody.Part.createFormData("alamatWali", siswa.alamatWali.toString())
                val phone = MultipartBody.Part.createFormData("phone", siswa.phone.toString())

                accountViewModel.addTeacher(session.token.toString(), account)
                    .observe(viewLifecycleOwner) {
                        if (it.status == "Success") {
                            siswaViewModel.addSiswa(
                                session.token.toString(),
                                name,
                                nis,
                                tempatLahir,
                                tanggalLahir,
                                address,
                                education,
                                religion,
                                gender,
                                namaAyah,
                                namaIbu,
                                pekerjaanAyah,
                                pekerjaanIbu,
                                namaWali,
                                pekerjaanWali,
                                alamatWali,
                                phone,
                                photo
                            )
                                .observe(viewLifecycleOwner) {
                                    if (it.status == "Success") {
                                        requireContext().showToast("Berhasil registrasi Siswa & Orang Tua")
                                        mainNavController.popBackStack(R.id.addSiswaFragment, true)
                                    } else {
                                        requireContext().showToast(it.message.toString())
                                    }
                                }
                        } else {
                            requireContext().showToast(it.message.toString())
                        }
                    }
            }
        }
    }

}