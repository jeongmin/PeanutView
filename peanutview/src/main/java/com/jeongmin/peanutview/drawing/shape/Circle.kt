package com.jeongmin.peanutview.drawing.shape

import android.graphics.PointF

/**
 * Created by jay on 17. 12. 24.
 */
class Circle{
    var cx: Float
    var cy: Float
    var radius: Float


    constructor(cx: Float, cy: Float, radius: Float) {
        this.cx = cx
        this.cy = cy
        this.radius = radius
    }

    constructor(point: PointF, radius: Float) {
        this.cx = point.x
        this.cy = point.y
        this.radius = radius
    }

    fun getPoint() = PointF(cx, cy)
}