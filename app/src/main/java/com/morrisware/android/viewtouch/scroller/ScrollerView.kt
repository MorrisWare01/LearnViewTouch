package com.morrisware.android.viewtouch.scroller

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.Scroller

/**
 * Created by MorrisWare on 2018/10/16.
 * Email: MorrisWare01@gmail.com
 */
class ScrollerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val scroller = Scroller(context, BounceInterpolator())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.color = Color.RED
        paint.textSize = 18f
    }

    fun startTranslationX(dx: Int, duration: Int) {
        scroller.startScroll(scrollX, 0, dx, 0, duration)
        invalidate()
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.drawRect(0f, 0f, width / 2f, height / 2f, paint)
        canvas.restore()
    }

}