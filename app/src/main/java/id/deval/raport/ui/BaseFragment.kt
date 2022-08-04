package id.deval.raport.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import id.deval.raport.R
import id.deval.raport.databinding.FragmentBaseBinding
import id.deval.raport.utils.BaseSkeletonFragment
import id.deval.raport.utils.HelperView
import id.deval.raport.utils.Role

class BaseFragment : BaseSkeletonFragment() {

    private lateinit var _binding: FragmentBaseBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            when (Role.TEACHER) {
                Role.ADMIN -> {
                    navigateToMenuAsAdmin()
                    true
                }
                Role.TEACHER -> {
                    navigateToMenuAsTeacher()
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            when (Role.TEACHER) {
                Role.ADMIN -> {
                    botnavBaseContainer.selectedItemId = R.id.akun
                    true
                }
                Role.TEACHER -> {
                    botnavBaseContainer.selectedItemId = R.id.absen
                    true
                }
                else -> false
            }

        }
    }


    fun navigateToMenuAsAdmin() {
        with(binding) {
            val navGraph = secNavController.navInflater.inflate(R.navigation.second_nav_graph)
            navGraph.setStartDestination(R.id.akunFragment)
            secNavController.graph = navGraph
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.akunFragment, true)
                .build()
            secNavController.navigate(R.id.akunFragment, bundleOf(), navOptions)

            botnavBaseContainer.setOnItemSelectedListener {
                secNavController.popBackStack(
                    secNavController.currentDestination?.id ?: R.id.akunFragment, true
                )
                when (it.itemId) {
                    R.id.akun -> {
                        secNavController.navigate(R.id.akunFragment)
                        true
                    }
                    R.id.kelas -> {
                        secNavController.navigate(R.id.kelasFragment)
                        true
                    }
                    R.id.mapel -> {
                        secNavController.navigate(R.id.mapelFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    fun navigateToMenuAsTeacher() {
        with(binding) {
            val navGraph = secNavController.navInflater.inflate(R.navigation.second_nav_graph)
            navGraph.setStartDestination(R.id.absenFragment)
            secNavController.graph = navGraph
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.absenFragment, true)
                .build()
            secNavController.navigate(R.id.absenFragment, bundleOf(), navOptions)

            botnavBaseContainer.menu.clear()
            botnavBaseContainer.inflateMenu(R.menu.menu_botnav_teacher)
            botnavBaseContainer.setOnItemSelectedListener {

                secNavController.popBackStack(
                    secNavController.currentDestination?.id ?: R.id.absenFragment, true
                )
                when (it.itemId) {
                    R.id.absen -> {
                        secNavController.navigate(R.id.absenFragment)
                        true
                    }
                    R.id.raport -> {
                        secNavController.navigate(R.id.raportFragment)
                        true
                    }
                    R.id.pesan -> {
                        secNavController.navigate(R.id.pesanFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }

}