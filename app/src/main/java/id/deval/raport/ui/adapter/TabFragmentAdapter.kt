package id.deval.raport.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabFragmentAdapter(fm: FragmentManager, behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) :
    FragmentPagerAdapter(fm, behavior) {

    var fragmentList = ArrayList<Fragment>()
    var fragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList.get(position)
    }

    fun addFragment(fragment: Fragment, title: String){
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }
}