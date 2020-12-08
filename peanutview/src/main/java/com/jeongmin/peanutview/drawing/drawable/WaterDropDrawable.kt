package com.jeongmin.peanutview.drawing.drawable

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF

/**
 * Created by jeongmin on 20. 11. 29.
 */
class WaterDropDrawable : SelfDrawable {

    private val center: PointF
    private val size: Float
    private val path: Path = Path()

    constructor(paint: Paint, center: PointF, size: Float) {
        this.paint = paint
        this.center = center
        this.size = size
    }

    override fun drawInitialState(canvas: Canvas) {
        drawBezierCircle(center.x, center.y, size)
        canvas.drawPath(path, paint)
    }

    override fun drawLastState(canvas: Canvas) {
        drawBezierCircle(center.x, center.y, size)
        canvas.drawPath(path, paint)
    }

    override fun drawInAnimation(canvas: Canvas, interpolation: Float) {
        // do nothing
        //drawBezierCircle(center.x, center.y, size)
        //canvas.drawPath(path, paint)
    }

    private fun drawBezierCircle(centerX: Float, centerY: Float, size: Float) {
        drawBezierOval(centerX, centerY, size, size)
    }

    private fun drawBezierOval(centerX: Float, centerY: Float, sizeX: Float, sizeY: Float) {
        drawBezierOvalQuarter(centerX, centerY, -sizeX, sizeY)
        drawBezierOvalQuarter(centerX, centerY, sizeX, sizeY)
        drawBezierOvalQuarter(centerX, centerY, sizeX, -sizeY)
        drawBezierOvalQuarter(centerX, centerY, -sizeX, -sizeY)
    }

    private fun drawBezierOvalQuarter(centerX: Float, centerY: Float, sizeX: Float, sizeY: Float) {
        path.moveTo(
                centerX - (sizeX),
                centerY - (0)
        )
        path.cubicTo(
                centerX - (sizeX),
                centerY - (0.552 * sizeY).toFloat(),
                centerX - (0.552 * sizeX).toFloat(),
                centerY - (sizeY),
                centerX - (0),
                centerY - (sizeY)
        )
    }
}