package com.jeongmin.peanutview.drawing.drawable

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import kotlin.math.pow

/**
 * Created by jeongmin on 2020. 12. 03.
 */
class BezierCircleDrawable : SelfDrawable {

    private var point: PointF = PointF()

    private val radius: Float

    private val point0: PointF
    private val point1: PointF
    private val point2: PointF
    private val point3: PointF?



    constructor(paint: Paint, radius: Float, point0: PointF, point1: PointF, point2: PointF, point3: PointF? = null) {
        this.paint = paint
        this.point0 = point0
        this.point1 = point1
        this.point2 = point2
        this.point3 = point3
        this.radius = radius
    }

    override fun drawInitialState(canvas: Canvas) {
        canvas.drawCircle(point0.x, point0.y, radius, paint)
    }

    override fun drawLastState(canvas: Canvas) {
        if (point3 != null) {
            canvas.drawCircle(point3.x, point3.y, radius, paint)
        } else {
            canvas.drawCircle(point2.x, point2.y, radius, paint)
        }
    }

    override fun drawInAnimation(canvas: Canvas, interpolation: Float) {
        if (point3 != null) {
            calcCubicBezierCurve(point0, point1, point2, point3, interpolation, point)
            canvas.drawCircle(point.x, point.y, radius, paint)
        } else {
            calcQuadBezierCurve(point0, point1, point2, interpolation, point)
            canvas.drawCircle(point.x, point.y, radius, paint)
        }
    }

    private fun calcQuadBezierCurve(p0: PointF, p1: PointF, p2: PointF, t: Float, ret: PointF) {
        ret.x = ((1 - t).toDouble().pow(2) * p0.x + 2 * (1 - t) * t * p1.x + t.toDouble().pow(2) * p2.x).toFloat()
        ret.y = ((1 - t).toDouble().pow(2) * p0.y + 2 * (1 - t) * t * p1.y + t.toDouble().pow(2) * p2.y).toFloat()
    }


    private fun calcCubicBezierCurve(p0: PointF, p1: PointF, p2: PointF, p3: PointF, t: Float, ret: PointF) {
        val left = PointF()
        val right = PointF()
        calcQuadBezierCurve(p0, p1, p2, t, left)
        calcQuadBezierCurve(p1, p2, p3, t, right)

        ret.x = (1 - t) * left.x + t * right.x
        ret.y = (1 - t) * left.y + t * right.y
    }

}