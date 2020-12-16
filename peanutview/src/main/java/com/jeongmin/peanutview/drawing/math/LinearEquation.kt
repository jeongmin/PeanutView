package com.jeongmin.peanutview.drawing.math

import android.graphics.PointF

/**
 * Created by jay on 17. 12. 28.
 */
class LinearEquation {
    var slope: Float = 0f
    var b: Float = 0f


    constructor(slope: Float, b: Float) {
        this.slope = slope
        this.b = b
    }

    /**
     * y - y1 = m(x - x1)
     * y = mx + (m * -x1) + y1
     */
    constructor(slope: Float, p: PointF) {
        this.slope = slope
        this.b = (slope * -p.x) + p.y
    }

    /**
     * Perpendicular slope
     * 직교하는 직선의 기울기
     */
    fun perpSlope(): Float {
        if (slope == 0f) {
            return Float.NaN
        }
        return -1 / slope
    }

    /**
     * 점 p를 지나며 직교하는 직선의 방정식
     */
    fun perpLinearEquation(p: PointF) = LinearEquation(perpSlope(), p)

    /**
     * y = ax + b
     * x = -(b/a)
     */
    fun interceptX(): Float = -(b/slope)
    fun interceptY(): Float = b
    fun getX(y: Float): Float = (y - b) / slope
    fun getX(y: Int): Float = (y - b) / slope
    fun getY(x: Float): Float = slope * x + b
    fun getY(x: Int): Float = slope * x + b
}