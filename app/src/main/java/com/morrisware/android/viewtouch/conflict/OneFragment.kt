package com.morrisware.android.viewtouch.conflict

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.morrisware.android.viewtouch.R
import kotlinx.android.synthetic.main.one_fragment.*

/**
 * Created by MorrisWare on 2018/10/16.
 * Email: MorrisWare01@gmail.com
 */
class OneFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.one_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPager.adapter = MyPagerAdapter()
    }

    class MyPagerAdapter : PagerAdapter() {
        companion object {
            val list = listOf("1", "2", "3")
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return `object` == view
        }

        override fun getCount(): Int {
            return list.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val textView = TextView(container.context)
            textView.text = list[position]
            container.addView(textView)
            return textView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View?)
        }
    }


}