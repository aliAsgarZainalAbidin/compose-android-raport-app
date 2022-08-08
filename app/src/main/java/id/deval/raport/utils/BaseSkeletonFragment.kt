package id.deval.raport.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.solver.widgets.Helper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import id.deval.raport.db.Session
import id.deval.raport.viewModels.LoginViewModel
import javax.inject.Inject

@AndroidEntryPoint
open class BaseSkeletonFragment : Fragment() {

    lateinit var mainNavController : NavController
    lateinit var secNavController: NavController
    val loginViewModel: LoginViewModel by viewModels()
    @Inject
    lateinit var session: Session

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainNavController = requireActivity().getMainNavController()
        secNavController = this.getSecNavController()
    }
}