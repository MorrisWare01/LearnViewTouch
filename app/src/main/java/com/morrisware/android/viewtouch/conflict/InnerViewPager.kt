package com.morrisware.android.viewtouch.conflict

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.viewpager.widget.ViewPager

/**
 * Created by MorrisWare on 2018/10/16.
 * Email: MorrisWare01@gmail.com
 */
class InnerViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    var lastX: Float = -1f

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        // 请求父控件不允许拦截
        parent.requestDisallowInterceptTouchEvent(true)
        val x = ev.x
        val slop = ViewConfiguration.get(context).scaledTouchSlop
        when (ev.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_MOVE -> {
                adapter?.apply {
                    if ((currentItem == 0 && x - lastX > slop) ||
                        (currentItem == count - 1 && x - lastX < slop)) {
                        // 请求父控件拦截
                        parent.requestDisallowInterceptTouchEvent(false)
                        // 返回false，不响应控件的onTouchEvent事件
                        return false
                    }
                }
            }
        }
        lastX = x
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        Log.d("Inner", "${ev.action}")
        return super.onTouchEvent(ev)
    }

}