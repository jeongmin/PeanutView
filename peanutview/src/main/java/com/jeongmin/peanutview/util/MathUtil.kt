package com.jeongmin.peanutview.util

import android.graphics.PointF
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt



fun slopeBetweenPoints(p1: PointF, p2: PointF) = (p2.y - p1.y) / (p2.x - p1.x)

fun perpSlop(slope: Float) = -1 / slope

fun areSlope(slope1: Float, slope2: Float) = (slope1 * slope2) == -1f

fun getLinearEquation(p1: PointF, p2: PointF): String {
    val slope: Float;

    slope = slopeBetweenPoints(p1, p2)
    return "y = $slope"
}

// 원과 직선의 교점
fun intersectionXwithCircle(r: Float, a: Float, b: Float, m: Float, n: Float): FloatArray {
    val A = 1 + a.pow(2)
    val B = 2 * (a*b - a*n - m)
    val C = m.pow(2) + n.pow(2) - 2*n*b + b.pow(2) - r.pow(2)

    val temp1 = (-B + sqrt(B.pow(2) - 4 * A * C)) / (2 * A)
    val temp2 = (-B - sqrt(B.pow(2) - 4 * A * C)) / (2 * A)

    if (temp1 < temp2) {
        return floatArrayOf(temp1, temp2)
    } else {
        return floatArrayOf(temp2, temp1)
    }
}

/**
 * y = ax + b
 */
fun getY(a: Float, b: Float, x: Float): Float = (a * x) + b

fun getAngle(start: PointF, end: PointF): Double {
    val dy = (end.y - start.y).toDouble()
    val dx = (end.x - start.x).toDouble()
    return atan2(dy, dx) * (180.0 / Math.PI)
}

fun distance2D(p1: PointF, p2: PointF): Float = sqrt((p2.x - p1.x).pow(2) + (p2.y - p1.y).pow(2))