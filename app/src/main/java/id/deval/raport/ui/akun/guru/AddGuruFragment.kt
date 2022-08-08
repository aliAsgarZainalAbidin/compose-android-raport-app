package id.deval.raport.ui.akun.guru

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddGuruBinding
import id.deval.raport.db.models.Account
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.HelperView
import id.deval.raport.utils.showToast

class AddGuruFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAddGuruBinding
    private val binding get() = _binding
    private var isValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddGuruBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ivAddguruBack.setOnClickListener {
                mainNavController.popBackStack()
            }
            mbAddguruSimpan.setOnClickListener {
                val namaLengkap = tietAddguruNamalengkap.text.toString()
                val nik = tietAddguruNik.text.toString()
                val password = tietAddguruPassword.text.toString()
                val alamat = tietAddguruAlamat.text.toString()
                val email = tietAddguruEmail.text.toString()
                val nomorHp = tietAddguruHp.text.toString()

                if (namaLengkap.isEmpty()) {
                    tietAddguruNamalengkap.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (nik.isEmpty()) {
                    tietAddguruNik.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (password.isEmpty()) {
                    tietAddguruPassword.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (alamat.isEmpty()) {
                    tietAddguruAlamat.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (email.isEmpty()) {
                    tietAddguruEmail.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (nomorHp.isEmpty()) {
                    tietAddguruHp.error = "Tidak Boleh Kosong"
                    isValid = false
                }

                if (namaLengkap.isNotEmpty() || nik.isNotEmpty() || password.isNotEmpty() || alamat.isNotEmpty() || email.isNotEmpty() || nomorHp.isNotEmpty()) {
                    isValid = true
                }

                if (isValid) {
                    val account = Account(
                        null,
                        password,
                        "Guru",
                        alamat,
                        password,
                        nomorHp,
                        namaLengkap,
                        email,
                        nik,
                        null
                    )

                    accountViewModel.addTeacher(session.token.toString(), account)
                        .observe(viewLifecycleOwner) {
                            requireContext().showToast("${it.message}, Berhasil menambahkan data Guru")
                            mainNavController.popBackStack()
                        }
                }

            }

        }
    }
}