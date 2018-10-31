package com.morrisware.android.viewtouch.shopdetail.container

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Scroller
import androidx.core.view.ViewCompat

class ScrollViewContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var topView: View? = null
    private var bottomView: View? = null
    private var mViewWidth = 0
    private var mViewHeight = 0
    private var isMeasured = false

    private val mScroller = Scroller(context)
    private var velocityTracker: VelocityTracker? = null

    private var moveY = 0
    private var lastY = 0
    private var currentViewIndex = 0
    private var canPullUp = false
    private var canPullDown = false

    private val topViewTouchListener = OnTouchListener { v, event ->
        if (v is ScrollView) {
            canPullUp = currentViewIndex == 0 &&
                (v.scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight)
        }
        false
    }
    private val bottomViewTouchListener = OnTouchListener { v, event ->
        if (v is ScrollView) {
            canPullDown = currentViewIndex == 1 && v.scrollY == 0
        }
        false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!isMeasured) {
            isMeasured = true
            mViewHeight = measuredHeight
            mViewWidth = measuredWidth
            topView = getChildAt(0)
            bottomView = getChildAt(1)
            topView?.setOnTouchListener(topViewTouchListener)
            bottomView?.setOnTouchListener(bottomViewTouchListener)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        topView?.let { top ->
            top.layout(0, 0, mViewWidth, top.measuredHeight)
            bottomView?.let { bottom ->
                bottom.layout(0, top.measuredHeight, mViewWidth,
                    top.measuredHeight + bottom.measuredHeight)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        velocityTracker?.addMovement(ev)
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain()
                }
                velocityTracker?.clear()
                mScroller.abortAnimation()
                lastY = ev.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val distance = ev.y.toInt() - lastY
                lastY = ev.y.toInt()
                if (canPullUp && currentViewIndex == 0) {
                    moveY += distance
                    if (moveY > 0) {
                        moveY = 0
                        currentViewIndex = 0
                    } else if (moveY < -mViewHeight) {
                        moveY = -mViewHeight
                        currentViewIndex = 1
                    }
                    if (moveY < -8) {
                        // 防止事件冲突
                        ev.action = MotionEvent.ACTION_CANCEL
                    }
                    scrollTo(scrollX, -moveY)
                } else if (canPullDown && currentViewIndex == 1) {
                    moveY += distance
                    if (moveY < -mViewHeight) {
                        moveY = -mViewHeight
                        currentViewIndex = 1
                    } else if (moveY > 0) {
                        moveY = 0
                        currentViewIndex = 0
                    }
                    if (moveY > -mViewHeight + 8) {
                        // 防止事件冲突
                        ev.action = MotionEvent.ACTION_CANCEL
                    }
                    scrollTo(scrollX, -moveY)
                }
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker?.apply {
                    Log.d("TAG", "currentIndex: $currentViewIndex moveY: $moveY")
                    computeCurrentVelocity(700)
                    val v = yVelocity
                    if (moveY == 0 || moveY == -mViewHeight) {
                        return@apply
                    }
                    var isAutoUp = false
                    var isAutoDown = false
                    if (Math.abs(v) < 1000) {
                        if (moveY <= -mViewHeight / 2) {
                            isAutoUp = true
                        } else if (moveY > -mViewHeight / 2) {
                            isAutoDown = true
                        }
                    } else {
                        if (v < 0) {
                            isAutoUp = true
                        } else {
                            isAutoDown = true
                        }
                    }
                    if (isAutoUp) {
                        mScroller.startScroll(0, moveY, 0, -mViewHeight - moveY, 1000)
                    } else if (isAutoDown) {
                        mScroller.startScroll(0, moveY, 0, 0 - moveY, 1000)
                    }
                    invalidate()
                }
            }
        }
        super.dispatchTouchEvent(ev)
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            moveY = mScroller.currY
            scrollTo(scrollX, -moveY)
            postInvalidate()
        }
    }

}