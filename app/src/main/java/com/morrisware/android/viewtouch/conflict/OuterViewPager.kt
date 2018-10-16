package com.morrisware.android.viewtouch.conflict

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by MorrisWare on 2018/10/16.
 * Email: MorrisWare01@gmail.com
 */
class OuterViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        Log.d("Outer", "${ev.action}")
        return super.onTouchEvent(ev)
    }



}