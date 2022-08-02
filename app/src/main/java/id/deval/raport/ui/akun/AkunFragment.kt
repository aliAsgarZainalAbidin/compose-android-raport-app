package id.deval.raport.ui.akun

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAkunBinding
import id.deval.raport.db.models.Account
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.RvAdapter
import id.deval.raport.utils.OperationsTypeRv

class AkunFragment : Fragment() {

    private lateinit var _binding: FragmentAkunBinding
    private val binding get() = _binding
    private val dataGuru = arrayListOf<Account>()
    private val dataSiswa = arrayListOf<Siswa>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDummy()
        with(binding) {
            mtvAkunName.text = "Admin"
            with(cvAkunContainer){
                mtvAkunGuru.text = dataGuru.size.toString()
                mtvAkunSiswa.text = dataSiswa.size.toString()
            }

            includeRvGuru.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Account>("guru", OperationsTypeRv.READ, 2)
                adapter.setData(dataGuru)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            includeRvSiswa.mtvRvlayoutTitle.text = "Siswa"
            includeRvSiswa.rvRvlayoutContainer.apply {
                val adapter = RvAdapter<Siswa>("siswa", OperationsTypeRv.READ, 2)
                adapter.setData(dataSiswa)
                adapter.notifyDataSetChanged()
                this.adapter = adapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    fun setDummy() {
        for (n in 1..5) {
            val guru = Account(
                "980213088132",
                "ArogaN61",
                "guru",
                "Jln. S. Sutoyo",
                "ArogaN61",
                "01288312",
                "Ali",
                "ali@gmail.com",
                "ArogaN61"
            )
            dataGuru.add(guru)
        }
        for (n in 1..5) {
            val siswa = Siswa(
                "980213088132",
                "Jln Sutoyo",
                "-",
                "Laki-laki",
                "-",
                "17 Juni 1999",
                "Jln. Wali",
                "Islam",
                "Ali",
                "asdasdas",
                "-",
                "Syam",
                "0192839",
                "Ali Asgar",
                "60200117039",
                "Onto",
                "PNS",
                "PNS"
            )
            dataSiswa.add(siswa)
        }
    }

}