package com.somethingfun.jay.peanut

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_canvas.*

class CanvasActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)

        morphingView.setOnClickListener {
            morphingView.toggle()
        }
    }
}