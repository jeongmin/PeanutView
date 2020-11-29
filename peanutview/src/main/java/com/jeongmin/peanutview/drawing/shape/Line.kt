package com.jeongmin.peanutview.drawing.shape

import android.graphics.PointF

/**
 * Created by jay on 17. 12. 24.
 */
class Line {
    var x1: Float
    var y1: Float
    var x2: Float
    var y2: Float

    constructor(x1: Float, y1: Float, x2: Float, y2: Float) {
        this.x1 = x1
        this.x2 = x2
        this.y1 = y1
        this.y2 = y2
    }

    constructor(p1: PointF, p2: PointF) {
        this.x1 = p1.x
        this.y1 = p1.y
        this.x2 = p2.x
        this.y2 = p2.y
    }
}