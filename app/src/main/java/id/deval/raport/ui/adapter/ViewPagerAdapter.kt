package id.deval.raport.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.deval.raport.ui.pesan.GrowthFragment
import id.deval.raport.ui.pesan.NoteFragment
import id.deval.raport.utils.Constanta

class ViewPagerAdapter(
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var siswaId: String = " "

    fun setArgument(data: String) {
        siswaId = data
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString(Constanta.ID, siswaId)

        return when (position) {
            0 -> {
                val fragment = GrowthFragment()
                fragment.arguments = bundle
                fragment
            }
            1 -> {
                val fragment = NoteFragment()
                fragment.arguments = bundle
                fragment
            }
            else -> {
                val fragment = GrowthFragment()
                fragment.arguments = bundle
                fragment
            }
        }
    }

}