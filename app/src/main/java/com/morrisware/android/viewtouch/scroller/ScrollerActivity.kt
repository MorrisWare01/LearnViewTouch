package com.morrisware.android.viewtouch.scroller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.morrisware.android.viewtouch.R
import kotlinx.android.synthetic.main.scroller_activity.*

/**
 * Created by MorrisWare on 2018/10/16.
 * Email: MorrisWare01@gmail.com
 */
class ScrollerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroller_activity)
        move.setOnClickListener {
            scrollView.startTranslationX(-20, 1000)
        }
    }

}