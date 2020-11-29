package com.jeongmin.peanutview.drawing.drawable

import android.graphics.Canvas
import android.graphics.Paint
import com.jeongmin.peanutview.drawing.shape.Line

/**
 * Created by jay on 17. 11. 10.
 */
class LineDrawable(paint: Paint,
                   val line1: Line,
                   val line2: Line? = null) : SelfDrawable() {

    override fun drawInitialState(canvas: Canvas) {
        canvas.drawLine(line1.x1, line1.y1, line1.x2, line1.y2, paint)
    }

    override fun drawLastState(canvas: Canvas) {
        if (alphaAnim != null) {
            paint.alpha = (alphaAnim.to * 255).toInt()
        }

        if (line2 != null) {
            canvas.drawLine(line2.x1, line2.y1, line2.x2, line2.y2, paint)
        } else {
            canvas.drawLine(line1.x1, line1.y1, line1.x2, line1.y2, paint)
        }
    }

    override fun drawInAnimation(canvas: Canvas, interpolation: Float) {
        val endX1 = line2?.x1 ?: line1.x1
        val endX2 = line2?.x2 ?: line1.x2
        val endY1 = line2?.y1 ?: line1.y1
        val endY2 = line2?.y2 ?: line1.y2

        val deltaX1 = endX1 - line1.x1
        val deltaX2 = endX2 - line1.x2
        val deltaY1 = endY1 - line1.y1
        val deltaY2 = endY2 - line1.y2
        val toX1 = deltaX1 * interpolation + line1.x1
        val toX2 = deltaX2 * interpolation + line1.x2
        val toY1 = deltaY1 * interpolation + line1.y1
        val toY2 = deltaY2 * interpolation + line1.y2
        setAlpha(alphaAnim, paint, interpolation)
        canvas.drawLine(toX1, toY1, toX2, toY2, paint)
    }

    init {
        setPaint(paint)
    }
}