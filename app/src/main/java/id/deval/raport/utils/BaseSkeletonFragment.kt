package id.deval.raport.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.solver.widgets.Helper
import androidx.fragment.app.Fragment
import androidx.navigation.NavController

open class BaseSkeletonFragment : Fragment() {

    lateinit var mainNavController : NavController
    lateinit var secNavController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainNavController = HelperView.getMainNavController(requireActivity())
        secNavController = HelperView.getSecNavController(this)
    }
}