package com.somethingfun.jay.peanut

import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jeongmin.peanutview.drawing.drawable.WaterDropDrawable
import com.somethingfun.jay.peanut.databinding.ActivityWaterdropBinding


class WaterdropActivity: AppCompatActivity() {

    private lateinit var binding: ActivityWaterdropBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaterdropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val waterDropDrawable = WaterDropDrawable(makePaint(0xffffffff.toInt()), PointF(500F, 500F), 100F).apply {
            setAnimDuration(100L)
        }
        binding.peanutView.addAnimatable(waterDropDrawable)
    }
}