package com.jeongmin.peanutview.drawing.drawable

import android.graphics.Canvas
import android.graphics.Paint
import com.jeongmin.peanutview.drawing.shape.Circle

/**
 * Created by jay on 17. 11. 11.
 */
class CircleDrawable : SelfDrawable {
    interface DrawEvent {
        fun onDraw(cx: Float, cy: Float, radius: Float)
    }

    var circleStart: Circle
    var circleEnd: Circle? = null
    var drawEvent: DrawEvent? = null

    var tX: Float? = null
    var tY: Float? = null

    constructor(paint: Paint, circleStart: Circle, circleEnd: Circle) {
        this.circleStart = circleStart
        this.circleEnd = circleEnd
        this.paint = paint
    }

    constructor(paint: Paint, circleStart: Circle) {
        this.circleStart = circleStart
        this.paint = paint
    }

    override fun drawInitialState(canvas: Canvas) {
        canvas.drawCircle(circleStart.cx, circleStart.cy, circleStart.radius, paint)
    }

    override fun drawLastState(canvas: Canvas) {
        if (circleEnd == null) {
            drawInitialState(canvas)
            return
        }
        canvas.drawCircle(circleEnd!!.cx, circleEnd!!.cy, circleEnd!!.radius, paint)
    }

    override fun drawInAnimation(canvas: Canvas, interpolation: Float) {
        if (circleEnd == null) {
            drawInitialState(canvas)
            return
        }
        val deltaCx = circleEnd!!.cx - circleStart.cx
        val deltaCy = circleEnd!!.cy - circleStart.cy
        val deltaRadius = circleEnd!!.radius - circleStart.radius
        val toCx = deltaCx * interpolation + circleStart.cx
        val toCy = deltaCy * interpolation + circleStart.cy
        val toRadius = deltaRadius * interpolation + circleStart.radius
        setAlpha(alphaAnim, paint, interpolation)
        canvas.drawCircle(toCx, toCy, toRadius, paint)

        drawEvent?.onDraw(toCx, toCy, toRadius)
        tX = toCx
        tY = toCy
    }
}