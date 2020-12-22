package com.somethingfun.jay.peanut

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.Interpolator
import androidx.appcompat.app.AppCompatActivity
import com.jeongmin.peanutview.drawing.drawable.CircleDrawable
import com.jeongmin.peanutview.drawing.drawable.LineDrawable
import com.jeongmin.peanutview.drawing.math.LinearEquation
import com.jeongmin.peanutview.drawing.shape.Circle
import com.jeongmin.peanutview.drawing.shape.Line
import com.jeongmin.peanutview.util.intersectionXwithCircle
import com.somethingfun.jay.peanut.databinding.ActivityToucheFfectsBinding


class TouchEffectActivity: AppCompatActivity() {

    private lateinit var binding: ActivityToucheFfectsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToucheFfectsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTouchEvent()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTouchEvent() {
        binding.peanutView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    handleTouchEvent(event.x, event.y)
                }
            }
            false
        }
    }

    private fun handleTouchEvent(x: Float, y: Float) {
        val interpolator = object: Interpolator {
            override fun getInterpolation(input: Float): Float {
                if (input <= 0.2f) return 0f
                else return input
            }
        }

        val radius = 15f
        val animationDuration = 600
        val paint = makePaint(0xffffff00.toInt(), 14f)
        val circleDrawable = CircleDrawable(paint, Circle(x, y, radius), Circle(x, y, 0f)).apply {
            setAnimDuration(animationDuration)
            setRetainAfterAnimation(false)
            setInterpolator(interpolator)
        }


        val innerCircle = Circle(x, y, 30f)
        val outerCircle = Circle(x, y, 60f)

        var le = LinearEquation(0f, PointF(x, y))
        var innerInterSection = intersectionXwithCircle(innerCircle.radius, le.slope, le.b, x, y)
        var outerInterSection = intersectionXwithCircle(outerCircle.radius, le.slope, le.b, x, y)

        val ld1 = LineDrawable(paint,
                Line(innerInterSection[0], le.getY(innerInterSection[0]), outerInterSection[0], le.getY(outerInterSection[0])),
                Line(outerInterSection[0], le.getY(outerInterSection[0]), outerInterSection[0], le.getY(outerInterSection[0]))
        ).apply {
            setAnimDuration(animationDuration)
            setRetainAfterAnimation(false)
            setInterpolator(interpolator)
        }
        val ld2 = LineDrawable(paint,
                Line(innerInterSection[1], le.getY(innerInterSection[1]), outerInterSection[1], le.getY(outerInterSection[1])),
                Line(outerInterSection[1], le.getY(outerInterSection[1]), outerInterSection[1], le.getY(outerInterSection[1]))
        ).apply {
            setAnimDuration(animationDuration)
            setRetainAfterAnimation(false)
            setInterpolator(interpolator)
        }

        le = LinearEquation(1f, PointF(x, y))
        innerInterSection = intersectionXwithCircle(innerCircle.radius, le.slope, le.b, x, y)
        outerInterSection = intersectionXwithCircle(outerCircle.radius, le.slope, le.b, x, y)

        val ld3 = LineDrawable(paint,
                Line(innerInterSection[0], le.getY(innerInterSection[0]), outerInterSection[0], le.getY(outerInterSection[0])),
                Line(outerInterSection[0], le.getY(outerInterSection[0]), outerInterSection[0], le.getY(outerInterSection[0]))
        ).apply {
            setAnimDuration(animationDuration)
            setRetainAfterAnimation(false)
            setInterpolator(interpolator)
        }
        val ld4 = LineDrawable(paint,
                Line(innerInterSection[1], le.getY(innerInterSection[1]), outerInterSection[1], le.getY(outerInterSection[1])),
                Line(outerInterSection[1], le.getY(outerInterSection[1]), outerInterSection[1], le.getY(outerInterSection[1]))
        ).apply {
            setAnimDuration(animationDuration)
            setRetainAfterAnimation(false)
            setInterpolator(interpolator)
        }

        val ld5 = LineDrawable(paint,
                Line(x, y + innerCircle.radius, x, y + outerCircle.radius),
                Line(x, y + outerCircle.radius, x, y + outerCircle.radius)
        ).apply {
            setAnimDuration(animationDuration)
            setRetainAfterAnimation(false)
            setInterpolator(interpolator)
        }
        val ld6 = LineDrawable(paint,
                Line(x, y - innerCircle.radius, x, y - outerCircle.radius),
                Line(x, y - outerCircle.radius, x, y - outerCircle.radius)
        ).apply {
            setAnimDuration(animationDuration)
            setRetainAfterAnimation(false)
            setInterpolator(interpolator)
        }

        le = LinearEquation(-1f, PointF(x, y))
        innerInterSection = intersectionXwithCircle(innerCircle.radius, le.slope, le.b, x, y)
        outerInterSection = intersectionXwithCircle(outerCircle.radius, le.slope, le.b, x, y)
        val ld7 = LineDrawable(paint,
                Line(innerInterSection[0], le.getY(innerInterSection[0]), outerInterSection[0], le.getY(outerInterSection[0])),
                Line(outerInterSection[0], le.getY(outerInterSection[0]), outerInterSection[0], le.getY(outerInterSection[0]))
        ).apply {
            setAnimDuration(animationDuration)
            setRetainAfterAnimation(false)
            setInterpolator(interpolator)
        }
        val ld8 = LineDrawable(paint,
                Line(innerInterSection[1], le.getY(innerInterSection[1]), outerInterSection[1], le.getY(outerInterSection[1])),
                Line(outerInterSection[1], le.getY(outerInterSection[1]), outerInterSection[1], le.getY(outerInterSection[1]))).apply {
            setAnimDuration(animationDuration)
            setRetainAfterAnimation(false)
            setInterpolator(interpolator)
        }

        binding.peanutView.startImmediate(listOf(
                circleDrawable,
                ld1, ld2, ld3, ld4, ld5, ld6, ld7, ld8
        ))
    }
}