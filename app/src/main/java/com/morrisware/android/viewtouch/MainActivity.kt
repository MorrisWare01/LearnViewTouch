package com.morrisware.android.viewtouch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.morrisware.android.viewtouch.conflict.ViewPagerConflictActivity
import com.morrisware.android.viewtouch.scroller.ScrollerActivity
import com.morrisware.android.viewtouch.shopdetail.ShopDetailActivity
import com.morrisware.android.viewtouch.touch.TouchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scroller.setOnClickListener {
            startActivity(Intent(this, ScrollerActivity::class.java))
        }
        touch.setOnClickListener {
            startActivity(Intent(this, TouchActivity::class.java))
        }
        conflict.setOnClickListener {
            startActivity(Intent(this, ViewPagerConflictActivity::class.java))
        }
        shopDetail.setOnClickListener {
            startActivity(Intent(this, ShopDetailActivity::class.java))
        }

        textView.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    logViewPosition(textView)
                }
            })
        textView.setOnClickListener {
            textView.scrollBy(-10, -10)
        }
    }

    /**
     * view的位置信息都是相对于父控件的
     * scrollX,scrollY指的是当前view的内容与view的位置的偏移量（移动正负）
     * scrollTo,scrollBy只改变view的内容位置，不改变view的布局
     */
    private fun logViewPosition(view: View) {
        Log.d(TAG, "view: ${view.id} " +
            "x:${view.x} y:${view.y} " +
            "translationX:${view.translationX} translationY:${view.translationY} " +
            "left:${view.left} top:${view.top} right:${view.right} bottom:${view.bottom}")
    }

}
