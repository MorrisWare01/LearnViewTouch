package com.morrisware.android.viewtouch.touch

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import androidx.appcompat.app.AppCompatActivity
import com.morrisware.android.viewtouch.R

/**
 * Created by MorrisWare on 2018/10/16.
 * Email: MorrisWare01@gmail.com
 */
class TouchActivity : AppCompatActivity() {

    companion object {
        const val TAG = "TouchActivity"
    }

    private val velocityTracker by lazy { VelocityTracker.obtain() }

    private val gestureDetector by lazy {
        GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                Log.d(TAG, "onFling $velocityX $velocityY")
                return super.onFling(e1, e2, velocityX, velocityY)
            }

            override fun onDoubleTap(e: MotionEvent?): Boolean {
                Log.d(TAG, "onDoubleTap")
                return super.onDoubleTap(e)
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.touch_activity)
        // 最小的滑动常量
        Log.d(TAG, "scaledTouchSlop: ${ViewConfiguration.get(this).scaledTouchSlop}")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        velocityTracker.addMovement(event)
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> velocityTracker.clear()
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, 100f)
                Log.d(TAG, "${velocityTracker.xVelocity} , ${velocityTracker.yVelocity} ")
            }
        }
        return true
    }

}