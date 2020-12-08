package com.jeongmin.peanutview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class WaterDropView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = 0xff00ffff.toInt()
    }
    private val path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        setAsOval()
        //setAsRectangle()
    }

    private fun setAsRectangle() {
        path.reset()
        path.moveTo(0f, 0f)
        path.lineTo(200f, 0f)
        path.lineTo(200f, 200f)
        path.lineTo(0f, 200f)
        path.lineTo(0f, 0f)
    }


    private fun setAsOval() {
        val end = 350f

        path.reset()
        //path.moveTo(0f, 0f)
        //path.arcTo(RectF(0f, 0f, 200f, 200f), 90f, 270f)
        path.addArc(RectF(0f, 0f, 200f, 200f), 180f, 180f)

        //path.moveTo(100f, 0f)
        //path.quadTo(200F, 0F, end, 100F)
        //path.quadTo(200F, 200F, 100F, 200F)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        canvas.drawPath(path, paint)

    }
}