package com.jeongmin.peanutview.util

import android.graphics.PointF

/**
 * Created by jay on 17. 12. 28.
 */


fun slopeBetweenPoints(p1: PointF, p2: PointF) = (p2.x - p1.x) / (p2.y - p1.y)

fun perpSlop(slope: Float) = -1 / slope

fun areSlope(slope1: Float, slope2: Float) = (slope1 * slope2) == -1f

fun getLinearEquation(p1: PointF, p2: PointF): String {
    val slope: Float;

    slope = slopeBetweenPoints(p1, p2)
    return "y = $slope"
}