package com.jeongmin.peanutview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class MorphingView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = 0xff000000.toInt()
    }
    private val path = Path()
    private var isRect = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        setShape()
    }

    fun toggle() {
        isRect = !isRect
        setShape()
        invalidate()
    }

    private fun setShape() {
        if (isRect) {
            setAsRect(width.toFloat(), height.toFloat())
        } else {
            setAsOval(width.toFloat(), height.toFloat())
        }
    }

    private fun setAsOval(w: Float, h: Float) {
        path.reset()
        path.moveTo(0f, h / 2f)
        path.quadTo(0f, 0f, w / 2f, 0f)
        path.moveTo(0f, h / 2f)
        path.quadTo(0f, h, w / 2f, h)

        path.moveTo(w, h / 2f)
        path.quadTo(w, 0f, w / 2f, 0f)
        path.moveTo(w, h / 2f)
        path.quadTo(w, h, w / 2f, h)

        path.moveTo(w / 2f, h)

    }

    private fun setAsRect(w: Float, h: Float) {
        path.reset()
        path.addRect(RectF(0f, 0f, w, h), Path.Direction.CW)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        canvas.drawPath(path, paint)

    }
}