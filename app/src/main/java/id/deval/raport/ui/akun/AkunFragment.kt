package id.deval.raport.ui.akun

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import id.deval.raport.R
import id.deval.raport.databinding.FragmentAkunBinding
import id.deval.raport.db.models.Account
import id.deval.raport.db.models.Siswa
import id.deval.raport.ui.adapter.RvAdapter
import id.deval.raport.utils.*

class AkunFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentAkunBinding
    private val binding get() = _binding
    private var dataGuru = arrayListOf<Account>()
    private var dataSiswa = arrayListOf<Siswa>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataGuru = DummyData().setDummyGuru()
        dataSiswa = DummyData().setDummyDataSiswa()

        with(binding) {
            mtvAkunName.text = session.name
            with(cvAkunContainer){
//                mtvAkunGuru.text = dataGuru.size.toString()
//                mtvAkunSiswa.text = dataSiswa.size.toString()
            }

            Log.d("TAG", "onViewCreated: ${session.token}")

            accountViewModel.getAllTeacher(session.token.toString()).observe(viewLifecycleOwner){
                with(binding){
                    if (it.isSuccessful){
                        ivAkunPerson.setOnClickListener {
                            val bundle = bundleOf()
                            bundle.putString(Constanta.ID, session.id)
                            bundle.putString(Constanta.ROLE, "admin")
                            mainNavController.navigate(R.id.action_baseFragment_to_addGuruFragment,bundle)
                        }

                        mtvAkunGuru.text = it.body()?.result.toString()
                        if (it.body()?.data!!.size == 0){
                            includeRvGuru.mtvRvlayoutViewmore.invisible()
                            includeRvGuru.mtvRvlayoutAdd.show()
                            includeRvGuru.mtvRvlayoutAdd.setOnClickListener {
                                mainNavController.navigate(R.id.action_baseFragment_to_registrasiGuruFragment)
                            }
                        } else {
                            includeRvGuru.mtvRvlayoutViewmore.show()
                            includeRvGuru.mtvRvlayoutAdd.invisible()
                            includeRvGuru.mtvRvlayoutViewmore.setOnClickListener {
                                mainNavController.navigate(R.id.action_baseFragment_to_registrasiGuruFragment)
                            }
                        }

                        includeRvGuru.rvRvlayoutContainer.apply {
                            val adapter = RvAdapter<Account>("guru", OperationsTypeRv.READ, mainNavController,2)
                            adapter.setData(it.body()?.data!!)
                            adapter.notifyDataSetChanged()
                            this.adapter = adapter
                            layoutManager =
                                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        }
                    } else {
                        requireContext().showToast(it.message())

                    }
                }
            }

            siswaViewModel.getAllSiswa(session.token.toString()).observe(viewLifecycleOwner){
                if (it.isSuccessful){
                    mtvAkunSiswa.text = it.body()?.data!!.size.toString()
                    if (it.body()?.data!!.size == 0){
                        includeRvSiswa.mtvRvlayoutViewmore.invisible()
                        includeRvSiswa.mtvRvlayoutAdd.show()
                        includeRvSiswa.mtvRvlayoutAdd.setOnClickListener {
                            mainNavController.navigate(R.id.action_baseFragment_to_registrasiSiswaFragment)
                        }
                    } else {
                        includeRvSiswa.mtvRvlayoutViewmore.show()
                        includeRvSiswa.mtvRvlayoutAdd.invisible()
                        includeRvSiswa.mtvRvlayoutViewmore.setOnClickListener {
                            mainNavController.navigate(R.id.action_baseFragment_to_registrasiSiswaFragment)
                        }
                    }
                    includeRvSiswa.mtvRvlayoutTitle.text = "Siswa"
                    includeRvSiswa.rvRvlayoutContainer.apply {
                        val adapter = RvAdapter<Siswa>("siswa", OperationsTypeRv.READ, mainNavController,2)
                        adapter.setData(it.body()?.data!!)
                        adapter.notifyDataSetChanged()
                        this.adapter = adapter
                        layoutManager =
                            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                } else {
                    requireContext().showToast(it.message())
                }
            }

        }
    }



}