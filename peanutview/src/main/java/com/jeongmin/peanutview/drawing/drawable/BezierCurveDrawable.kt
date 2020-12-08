package com.jeongmin.peanutview.drawing.drawable

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.Log
import com.jeongmin.peanutview.drawing.shape.Line
import kotlin.math.pow

/**
 * Created by jay on 17. 11. 10.
 */
class BezierCurveDrawable(paint: Paint,
                          private val poionts: List<PointF>) : SelfDrawable() {

    private val path: Path = Path()

    override fun drawInitialState(canvas: Canvas) {
        if (poionts.size == 3) {
            path.moveTo(poionts[0].x, poionts[0].y)
            path.quadTo(poionts[1].x, poionts[1].y, poionts[2].x, poionts[2].y)
            canvas.drawPath(path, paint)
        } else if (poionts.size == 4) {
            path.moveTo(poionts[0].x, poionts[0].y)
            path.cubicTo(poionts[1].x, poionts[1].y, poionts[2].x, poionts[2].y, poionts[3].x, poionts[3].y)
            canvas.drawPath(path, paint)
        }
    }

    override fun drawLastState(canvas: Canvas) {
        if (poionts.size == 3) {
            path.moveTo(poionts[0].x, poionts[0].y)
            path.quadTo(poionts[1].x, poionts[1].y, poionts[2].x, poionts[2].y)
            canvas.drawPath(path, paint)
        } else if (poionts.size == 4) {
            path.moveTo(poionts[0].x, poionts[0].y)
            path.cubicTo(poionts[1].x, poionts[1].y, poionts[2].x, poionts[2].y, poionts[3].x, poionts[3].y)
            canvas.drawPath(path, paint)
        }
        Log.d("jm.lee", "drawLastState")
    }

    override fun drawInAnimation(canvas: Canvas, interpolation: Float) {

    }

    init {
        setPaint(paint)
    }
}