package id.deval.raport.ui.akun.siswa

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.google.gson.Gson
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddOrangTuaBinding
import id.deval.raport.db.models.Account
import id.deval.raport.db.models.Siswa
import id.deval.raport.db.models.request.AccountUpdate
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.showToast
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*
import javax.inject.Inject

class AddOrangTuaFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAddOrangTuaBinding
    private var isUpdateMode = false
    private val binding get() = _binding
    private var isValid = false

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
        val username = arguments?.getString(Constanta.USERNAME)
        var id = ""

        Log.d("TAG", "onViewCreated: $siswa")
        with(binding) {
            if (!username.isNullOrEmpty()) {
                isUpdateMode = true
                accountViewModel.getAccountByUsername(session.token.toString(), username)
                    .observe(viewLifecycleOwner) {
                        if (it.isSuccessful) {
                            val account = it?.body()?.data!!
                            tietAddorangtuaNamalengkap.setText(account.name)
                            tietAddorangtuaNisn.setText(account.username)
                            tietAddorangtuaTanggalLahir.setText(account.tanggalLahir)
                            tietAddorangtuaAlamat.setText(account.address)
                            tietAddorangtuaEmail.setText(account.email)
                            tietAddorangtuaHp.setText(account.phone)
                            id = it?.body()?.data!!.id.toString()
                        }
                    }
            }

            tietAddorangtuaTanggalLahir.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_WEEK)

                val datePickerDialog =
                    DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                        override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                            tietAddorangtuaTanggalLahir.setText("$p3-$p2-$p1")
                        }

                    }, year, month, day)
                datePickerDialog.show()
            }

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
                val tanggalLahir = tietAddorangtuaTanggalLahir.text.toString()

                if (namaLengkap.isEmpty()) {
                    isValid = false
                    tietAddorangtuaNamalengkap.error = resources.getString(R.string.tiet_empty)
                }
                if (password.isEmpty()) {
                    isValid = false
                    tietAddorangtuaPassword.error = resources.getString(R.string.tiet_empty)
                }
                if (nomorHp.isEmpty()) {
                    isValid = false
                    tietAddorangtuaHp.error = resources.getString(R.string.tiet_empty)
                }
                if (email.isEmpty()) {
                    isValid = false
                    tietAddorangtuaEmail.error = resources.getString(R.string.tiet_empty)
                }
                if (nik.isEmpty()) {
                    isValid = false
                    tietAddorangtuaNisn.error = resources.getString(R.string.tiet_empty)
                }
                if (tanggalLahir.isEmpty()) {
                    isValid = false
                    tietAddorangtuaTanggalLahir.error = resources.getString(R.string.tiet_empty)
                }

                if (namaLengkap.isNotEmpty() && password.isNotEmpty() && nomorHp.isNotEmpty() && email.isNotEmpty() && nik.isNotEmpty() && tanggalLahir.isNotEmpty()) {
                    isValid = true
                }

                if (isValid) {

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
                        tanggalLahir
                    )
                    if (!isUpdateMode) {
                        val imageBitmap = File(path)
                        val requestImageBody =
                            imageBitmap.asRequestBody("image/jpeg".toMediaTypeOrNull())
                        val photo = MultipartBody.Part.createFormData(
                            "photo",
                            imageBitmap.name,
                            requestImageBody
                        )
                        val name =
                            MultipartBody.Part.createFormData("name", siswa!!.name.toString())
                        val nis = MultipartBody.Part.createFormData("nis", siswa.nis.toString())
                        val tempatLahir =
                            MultipartBody.Part.createFormData(
                                "tempatLahir",
                                siswa.tempatLahir.toString()
                            )
                        val tanggalLahir =
                            MultipartBody.Part.createFormData(
                                "tanggalLahir",
                                siswa.tanggalLahir.toString()
                            )
                        val address =
                            MultipartBody.Part.createFormData("address", siswa.address.toString())
                        val education =
                            MultipartBody.Part.createFormData(
                                "education",
                                siswa.education.toString()
                            )
                        val religion =
                            MultipartBody.Part.createFormData("religion", siswa.religion.toString())
                        val gender =
                            MultipartBody.Part.createFormData("gender", siswa.gender.toString())
                        val namaAyah =
                            MultipartBody.Part.createFormData("namaAyah", siswa.namaAyah.toString())
                        val namaIbu =
                            MultipartBody.Part.createFormData("namaIbu", siswa.namaIbu.toString())
                        val pekerjaanAyah = MultipartBody.Part.createFormData(
                            "pekerjaanAyah",
                            siswa.pekerjaanAyah.toString()
                        )
                        val pekerjaanIbu =
                            MultipartBody.Part.createFormData(
                                "pekerjaanIbu",
                                siswa.pekerjaanIbu.toString()
                            )
                        val namaWali =
                            MultipartBody.Part.createFormData("namaWali", siswa.namaWali.toString())
                        val pekerjaanWali = MultipartBody.Part.createFormData(
                            "pekerjaanWali",
                            siswa.pekerjaanWali.toString()
                        )
                        val alamatWali =
                            MultipartBody.Part.createFormData(
                                "alamatWali",
                                siswa.alamatWali.toString()
                            )
                        val phone =
                            MultipartBody.Part.createFormData("phone", siswa.phone.toString())

                        accountViewModel.addTeacher(session.token.toString(), account)
                            .observe(viewLifecycleOwner) {
                                if (it.isSuccessful) {
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
                                            if (it.isSuccessful) {
                                                requireContext().showToast("Berhasil registrasi Siswa & Orang Tua")
                                                mainNavController.popBackStack(
                                                    R.id.addSiswaFragment,
                                                    true
                                                )
                                            } else {
                                                requireContext().showToast(it.message())
                                            }
                                        }
                                } else {
                                    requireContext().showToast(it.message())
                                }
                            }
                    } else {
                        val accountUpdate =
                            AccountUpdate(
                                password,
                                alamat,
                                nomorHp,
                                namaLengkap,
                                email,
                                tanggalLahir
                            )
                        accountViewModel.updateTeacher(session.token.toString(), id, accountUpdate)
                            .observe(viewLifecycleOwner) {
                                if (it.isSuccessful) {
                                    requireContext().showToast("${it.body()?.status}, Berhasil memperbaharui data OrangTua")
                                    mainNavController.popBackStack(R.id.addSiswaFragment, true)
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