package com.morrisware.android.viewtouch.conflict

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.morrisware.android.viewtouch.R
import kotlinx.android.synthetic.main.view_pager_conflict_activity.*

/**
 * Created by MorrisWare on 2018/10/16.
 * Email: MorrisWare01@gmail.com
 */
class ViewPagerConflictActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_pager_conflict_activity)
        viewPager.adapter = MyFragmentPagerAdapter(supportFragmentManager,
            listOf(TwoFragment(), OneFragment(), TwoFragment()))
    }

    class MyFragmentPagerAdapter(
        fragmentManager: FragmentManager,
        private val fragments: List<Fragment>
    ) : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.count();
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            return super.instantiateItem(container, position)
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
        }

    }


}