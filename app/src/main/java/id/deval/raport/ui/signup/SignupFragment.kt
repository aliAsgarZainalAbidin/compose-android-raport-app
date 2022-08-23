package id.deval.raport.ui.signup

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deval.raport.R
import id.deval.raport.databinding.FragmentSignupBinding
import id.deval.raport.db.models.Account
import id.deval.raport.db.models.request.AccountUpdate
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.hideError
import id.deval.raport.utils.showToast

class SignupFragment : BaseSkeletonFragment() {

    private lateinit var _binding : FragmentSignupBinding
    private val binding get() = _binding
    private var isValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            ivSignupBack.setOnClickListener {
                try {
                    mainNavController.popBackStack()
                }catch (e: Exception){
                    Log.d(ContentValues.TAG, "onViewCreated: $e")
                }
            }
            tietSignupNamalengkap.hideError()
            tietSignupNik.hideError()
            tietSignupPassword.hideError()
            tietSignupAlamat.hideError()
            tietSignupEmail.hideError()
            tietSignupHp.hideError()

            mbSignupSimpan.setOnClickListener {
                val namaLengkap = tietSignupNamalengkap.text.toString()
                val nik = tietSignupNik.text.toString()
                val password = tietSignupPassword.text.toString()
                val alamat = tietSignupAlamat.text.toString()
                val email = tietSignupEmail.text.toString()
                val nomorHp = tietSignupHp.text.toString()

                if (namaLengkap.isEmpty()) {
                    tietSignupNamalengkap.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (nik.isEmpty()) {
                    tietSignupNik.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (password.isEmpty()) {
                    tietSignupPassword.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (alamat.isEmpty()) {
                    tietSignupAlamat.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (email.isEmpty()) {
                    tietSignupEmail.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (nomorHp.isEmpty()) {
                    tietSignupHp.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (namaLengkap.isNotEmpty() || nik.isNotEmpty() || password.isNotEmpty() || alamat.isNotEmpty() || email.isNotEmpty() || nomorHp.isNotEmpty()) {
                    isValid = true
                }

                if (isValid) {
                    val account = Account(
                        null,
                        password,
                        "Admin",
                        alamat,
                        password,
                        nomorHp,
                        namaLengkap,
                        email,
                        nik,
                        null
                    )

                    Log.d("TAG", "onViewCreated: $id")
                    accountViewModel.signup(account)
                        .observe(viewLifecycleOwner) {
                            if (it.isSuccessful){
                                try {
                                    requireContext().showToast("${it.body()?.status}, Berhasil menambahkan Admin")
                                    mainNavController.popBackStack()
                                }catch (e: Exception){
                                    Log.d(ContentValues.TAG, "onViewCreated: $e")
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