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
import id.deval.raport.db.event.CommonParams
import id.deval.raport.viewModels.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

@AndroidEntryPoint
open class BaseSkeletonFragment : Fragment() {
    lateinit var mainNavController: NavController
    lateinit var secNavController: NavController
    val loginViewModel: LoginViewModel by viewModels()
    val accountViewModel: AccountViewModel by viewModels()
    val siswaViewModel: SiswaViewModel by viewModels()
    val kelasViewModel: KelasViewModel by viewModels()
    val mapelViewModel : MapelViewModel by viewModels()
    val absenViewModel : AbsenViewModel by viewModels()
    val raportViewModel : RaportViewModel by viewModels()
    val bus = EventBus.getDefault()
    @Inject lateinit var session: Session

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainNavController = requireActivity().getMainNavController()
        secNavController = this.getSecNavController()
    }

    override fun onResume() {
        super.onResume()
        bus.register(this)
    }

    override fun onPause() {
        super.onPause()
        bus.unregister(this)
    }

    @Subscribe
    open fun deleteItem(commonParams: CommonParams){}
}