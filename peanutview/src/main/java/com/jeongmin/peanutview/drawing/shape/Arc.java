package com.jeongmin.peanutview.drawing.shape;

import android.graphics.RectF;

/**
 * Created by jay on 18. 1. 26.
 */

public class Arc {
    public RectF oval;
    public float startAngle;
    public float sweepAngle;
    public boolean useCenter = true;

    public Arc() {
        oval = new RectF();
    }

    public static void getIntermediateArc(Arc start, Arc end, Arc intermediate, float interpolation) {
        intermediate.startAngle = (end.startAngle - start.startAngle) * interpolation + start.startAngle;
        intermediate.sweepAngle = (end.sweepAngle - start.sweepAngle) * interpolation + start.sweepAngle;

        intermediate.oval.left = (end.oval.left - start.oval.left) * interpolation + start.oval.left;
        intermediate.oval.right = (end.oval.right - start.oval.right) * interpolation + start.oval.right;
        intermediate.oval.top = (end.oval.top - start.oval.top) * interpolation + start.oval.top;
        intermediate.oval.bottom = (end.oval.bottom - start.oval.bottom) * interpolation + start.oval.bottom;
    }

}
