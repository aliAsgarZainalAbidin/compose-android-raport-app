package id.deval.raport.ui.akun.siswa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAddOrangTuaBinding
import id.deval.raport.db.models.Account
import id.deval.raport.db.models.Siswa
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.Constanta
import id.deval.raport.utils.showToast

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

                accountViewModel.addTeacher(session.token.toString(), account)
                    .observe(viewLifecycleOwner) {
                        if (it.status == "Success") {
                            siswaViewModel.addSiswa(session.token.toString(), siswa!!)
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