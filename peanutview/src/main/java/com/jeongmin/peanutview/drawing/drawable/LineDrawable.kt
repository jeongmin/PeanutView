package com.jeongmin.peanutview.drawing.drawable

import android.graphics.Canvas
import android.graphics.Paint
import com.jeongmin.peanutview.drawing.shape.Line

/**
 * Created by jay on 17. 11. 10.
 */
class LineDrawable(paint: Paint,
                   val line: Line,
                   val finalLine: Line? = null) : SelfDrawable() {

    var tX1: Float = 0F
    var tX2: Float = 0F
    var tY1: Float = 0F
    var tY2: Float = 0F

    override fun drawInitialState(canvas: Canvas) {
        canvas.drawLine(line.x1, line.y1, line.x2, line.y2, paint)
    }

    override fun drawLastState(canvas: Canvas) {
        if (alphaAnim != null) {
            paint.alpha = (alphaAnim.to * 255).toInt()
        }

        if (finalLine != null) {
            canvas.drawLine(finalLine.x1, finalLine.y1, finalLine.x2, finalLine.y2, paint)
        } else {
            canvas.drawLine(line.x1, line.y1, line.x2, line.y2, paint)
        }
    }

    override fun drawInAnimation(canvas: Canvas, interpolation: Float) {
        val endX1 = finalLine?.x1 ?: line.x1
        val endX2 = finalLine?.x2 ?: line.x2
        val endY1 = finalLine?.y1 ?: line.y1
        val endY2 = finalLine?.y2 ?: line.y2

        val deltaX1 = endX1 - line.x1
        val deltaX2 = endX2 - line.x2
        val deltaY1 = endY1 - line.y1
        val deltaY2 = endY2 - line.y2
        val toX1 = deltaX1 * interpolation + line.x1
        val toX2 = deltaX2 * interpolation + line.x2
        val toY1 = deltaY1 * interpolation + line.y1
        val toY2 = deltaY2 * interpolation + line.y2
        setAlpha(alphaAnim, paint, interpolation)
        canvas.drawLine(toX1, toY1, toX2, toY2, paint)

        tX1 = toX1
        tX2 = toX2
        tY1 = toY1
        tY2 = toY2
    }

    init {
        setPaint(paint)
    }
}